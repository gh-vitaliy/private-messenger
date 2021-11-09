package com.og.privatemessenger.model.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.util.*
import javax.inject.Inject


private const val TAG = "BluetoothClientThread"

class BluetoothServerThread
@Inject constructor(private val bluetoothAdapter: BluetoothAdapter) : Thread() {

    private val mmServerSocket: BluetoothServerSocket? by lazy(LazyThreadSafetyMode.NONE) {
        bluetoothAdapter
            .listenUsingInsecureRfcommWithServiceRecord(
                bluetoothAdapter.name,
                UUID.nameUUIDFromBytes(bluetoothAdapter.name.toByteArray())
            )
    }

    override fun run() {
        var shouldLoop = true
        while (shouldLoop) {
            val socket: BluetoothSocket? = try {
                mmServerSocket?.accept()
            } catch (e: IOException) {
                Log.e(TAG, "Socket's accept() method failed", e)
                shouldLoop = false
                null
            }
            socket?.also {
                //  manageMyConnectedSocket(it)
                mmServerSocket?.close()
                shouldLoop = false
            }
        }
    }

    // Closes the connect socket and causes the thread to finish.
    fun cancel() {
        try {
            mmServerSocket?.close()
        } catch (e: IOException) {
            Log.e(TAG, "Could not close the connect socket", e)
        }
    }

}