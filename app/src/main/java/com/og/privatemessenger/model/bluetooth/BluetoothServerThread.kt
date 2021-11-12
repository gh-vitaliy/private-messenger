package com.og.privatemessenger.model.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.util.Log
import androidx.lifecycle.MutableLiveData
import java.io.IOException
import java.util.*
import javax.inject.Inject


private const val TAG = "BluetoothClientThread"

class BluetoothServerThread
@Inject constructor(
    private val bluetoothAdapter: BluetoothAdapter,
    bluetoothDevice: BluetoothDevice
) : Thread() {

    private val mmServerSocket: BluetoothServerSocket? by lazy(LazyThreadSafetyMode.NONE) {
        bluetoothAdapter
            .listenUsingInsecureRfcommWithServiceRecord(
                bluetoothAdapter.name,
                UUID.nameUUIDFromBytes(bluetoothDevice.name.toByteArray())
            )
    }

    val isConnected: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }

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
            isConnected.postValue(true)
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
            Log.e(TAG, e.message, e)
        }
    }

}