package com.og.privatemessenger.model.bluetooth

import android.util.Log
import java.io.IOException

private const val TAG = "BluetoothDataTransferThread"

class BluetoothDataTransferThread(private val bluetoothDataTransferService: BluetoothDataTransferService) : Thread() {
    override fun run() {
    }

    // Call this method from the main activity to shut down the connection.
    fun cancel() {
        try {
           // socket.close()
        } catch (e: IOException) {
            Log.e(TAG, "Could not close the connect socket", e)
        }
    }
}