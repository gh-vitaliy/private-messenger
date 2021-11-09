package com.og.privatemessenger.model.bluetooth

import android.bluetooth.BluetoothSocket
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.og.privatemessenger.model.util.MessageConstants
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.security.PublicKey

private const val TAG = "BluetoothDataTransferService"

class BluetoothDataTransferService(socket: BluetoothSocket) {

    val bluetoothSocket = socket
    private val handler = Looper.myLooper()?.let { Handler(it) }

    private val inputStream: InputStream = socket.inputStream
    private val outputStream: OutputStream = socket.outputStream
    private val buffer: ByteArray = ByteArray(1024) // mmBuffer store for the stream

    fun read() {
        var numBytes: Int // bytes returned from read()

        // Keep listening to the InputStream until an exception occurs
        while (true) {
            // Read from the InputStream.
            numBytes = try {
                inputStream.read(buffer)
            } catch (e: IOException) {
                Log.d(TAG, "Input stream was disconnected", e)
                break
            }

            // Send the obtained bytes to the UI activity.
            val readMsg = handler?.obtainMessage(
                MessageConstants.MESSAGE_READ,
                numBytes,
                -1,
                buffer
            )
            readMsg?.sendToTarget()
        }
    }

    //sent message
    fun sent(message: String) {
        try {
            outputStream.write(message.toByteArray())
        } catch (e: IOException) {
            Log.e(TAG, "Error occurred when sending data", e)

            // Send a failure message back to the activity.
            val writeErrorMsg = handler?.obtainMessage(MessageConstants.MESSAGE_TOAST)
            val bundle = Bundle().apply {
                putString("toast", "Couldn't send data to the other device")
            }
            writeErrorMsg?.data = bundle
            writeErrorMsg?.let { handler?.sendMessage(it) }
            return
        }

        // Share the sent message with the UI activity.
        val writtenMsg = handler?.obtainMessage(
            MessageConstants.MESSAGE_WRITE,
            -1,
            -1,
            buffer
        )
        writtenMsg?.sendToTarget()
    }
}