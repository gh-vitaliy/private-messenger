package com.og.privatemessenger.model.bluetooth

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import android.widget.Toast
import com.og.privatemessenger.model.PrivateMessengerApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*
import javax.inject.Inject

private const val TAG = "BluetoothClientThread"

class BluetoothClientThread @Inject constructor(bluetoothDevice: BluetoothDevice) : Thread() {

    private val mmSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
        bluetoothDevice.createInsecureRfcommSocketToServiceRecord(UUID.nameUUIDFromBytes("Maxim".toByteArray()))
    }

    override fun run() {

        mmSocket?.let { socket ->
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