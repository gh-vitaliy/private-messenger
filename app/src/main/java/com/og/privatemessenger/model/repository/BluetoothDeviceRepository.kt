package com.og.privatemessenger.model.repository

import android.bluetooth.BluetoothDevice
import com.og.privatemessenger.model.bluetooth.BluetoothClientThread

class BluetoothDeviceRepository {
    suspend fun connect(bluetoothDevice: BluetoothDevice) {
        BluetoothClientThread(bluetoothDevice).run()
    }
}