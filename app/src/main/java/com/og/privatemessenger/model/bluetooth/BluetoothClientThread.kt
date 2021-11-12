package com.og.privatemessenger.model.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import com.og.privatemessenger.model.di.components.DaggerBluetoothDeviceListComponent
import java.io.IOException
import java.util.*
import javax.inject.Inject

private const val TAG = "BluetoothClientThread"

class BluetoothClientThread(bluetoothDevice: BluetoothDevice) :
    Thread() {

    @Inject
    lateinit var bluetoothAdapter: BluetoothAdapter

    init {
        DaggerBluetoothDeviceListComponent.create().inject(this)
    }

    private val mmSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
        bluetoothDevice.createInsecureRfcommSocketToServiceRecord(
            UUID.nameUUIDFromBytes(bluetoothAdapter.name.toByteArray())
        )
    }

    override fun run() {

        mmSocket?.let { socket ->
            if (socket.isConnected)
                socket.close()

            socket.connect()
            Log.d(TAG, "Connected ${socket.remoteDevice.let { "${it.name} ${it.address}" }}")

            //manageMyConnectedSocket(socket)
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