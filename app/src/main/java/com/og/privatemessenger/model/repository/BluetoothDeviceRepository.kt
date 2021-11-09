package com.og.privatemessenger.model.repository

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.og.privatemessenger.model.bluetooth.BluetoothClientThread
import kotlinx.coroutines.*
import java.io.IOException
import javax.inject.Inject

class BluetoothDeviceRepository{
    suspend fun connect(bluetoothDevice: BluetoothDevice) {
        BluetoothClientThread(bluetoothDevice).run()
    }
}