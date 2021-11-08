package com.og.privatemessenger.model.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.util.*
import javax.inject.Inject


private const val SERVER_TAG = "BluetoothServerThread"
private const val CLIENT_TAG = "BluetoothClientThread"

class BluetoothService @Inject constructor(private val bluetoothAdapter: BluetoothAdapter) {

    inner class BluetoothClientThread(bluetoothDevice: BluetoothDevice) : Thread() {

        private val mmSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
            bluetoothDevice.createRfcommSocketToServiceRecord(UUID.nameUUIDFromBytes("Maxim".toByteArray()))
        }

        override fun run() {
            // Cancel discovery because it otherwise slows down the connection.

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
                Log.e(CLIENT_TAG, "Could not close the client socket", e)
            }
        }
    }


    inner class BluetoothServerThread : Thread() {

        private val mmServerSocket: BluetoothServerSocket? by lazy(LazyThreadSafetyMode.NONE) {
            bluetoothAdapter
                .listenUsingInsecureRfcommWithServiceRecord(
                    "Maxim", UUID.nameUUIDFromBytes("Maxim".toByteArray())
                )
        }

        override fun run() {
            // Keep listening until exception occurs or a socket is returned.
            var shouldLoop = true
            while (shouldLoop) {
                val socket: BluetoothSocket? = try {
                    mmServerSocket?.accept()
                } catch (e: IOException) {
                    Log.e(CLIENT_TAG, "Socket's accept() method failed", e)
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
                Log.e(CLIENT_TAG, "Could not close the connect socket", e)
            }
        }

    }

}