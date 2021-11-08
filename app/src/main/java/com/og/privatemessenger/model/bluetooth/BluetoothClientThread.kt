package com.og.privatemessenger.model.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.util.*
import javax.inject.Inject

private const val TAG = "BluetoothClientThread"

class BluetoothClientThread(bluetoothDevice: BluetoothDevice) : Thread() {

    @Inject
    lateinit var bluetoothAdapter: BluetoothAdapter

    private val mmSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
        bluetoothDevice.createRfcommSocketToServiceRecord(UUID.nameUUIDFromBytes("Maxim".toByteArray()))
    }

    override fun run() {
        // Cancel discovery because it otherwise slows down the connection.
        print(bluetoothAdapter.address.toString())

        mmSocket?.let { socket ->
            // Connect to the remote device through the socket. This call blocks
            // until it succeeds or throws an exception.
            socket.connect()

            // The connection attempt succeeded. Perform work associated with
            // the connection in a separate thread.
            //   manageMyConnectedSocket(socket)
        }
    }

    // Closes the client socket and causes the thread to finish.
    fun cancel() {
        try {
            mmSocket?.close()
        } catch (e: IOException) {
            Log.e(TAG, "Could not close the client socket", e)
        }
    }
}