package com.og.privatemessenger.model.bluetooth

import android.bluetooth.BluetoothSocket
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.og.privatemessenger.model.entity.Message
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

private const val TAG = "BluetoothDataTransferThread"

class BluetoothDataTransferThread
@Inject constructor(private val handler: Handler, private val socket: BluetoothSocket) :
    Thread() {
    
    private val inputStream: InputStream = socket.inputStream
    private val outputStream: OutputStream = socket.outputStream
    private val buffer: ByteArray = ByteArray(1024)

    override fun run() {
        read()
    }

    fun read() {
        var numBytes: Int

        //listening before disconnect
        while (true) {
            numBytes = try {
                inputStream.read(buffer)
            } catch (e: IOException) {
                Log.d(TAG, "Input stream was disconnected", e)
                break
            }

            //show message in UI
            val readMsg = handler.obtainMessage(
                Message.MESSAGE_READ,
                numBytes,
                -1,
                buffer
            )
            readMsg.sendToTarget()
        }
    }

    //send message
    fun send(message: Message) {
        try {
            outputStream.write(message.toString().toByteArray())
        } catch (e: IOException) {
            Log.e(TAG, "Error occurred when sending data", e)
            val writeErrorMsg = handler.obtainMessage(Message.MESSAGE_TOAST)
            val bundle = Bundle().apply {
                putString("toast", "Couldn't send data to the other device")
            }
            writeErrorMsg.data = bundle
            writeErrorMsg.let { handler.sendMessage(it) }
            return
        }

        //send message to UI
        val writtenMsg = handler.obtainMessage(
            Message.MESSAGE_SEND,
            -1,
            -1,
            buffer
        )
        writtenMsg.sendToTarget()
    }

    //close connection
    fun cancel() {
        try {
            socket.close()
        } catch (e: IOException) {
            Log.e(TAG, "Could not close the connect socket", e)
        }
    }


}