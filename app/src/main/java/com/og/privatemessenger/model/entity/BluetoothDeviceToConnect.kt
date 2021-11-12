package com.og.privatemessenger.model.entity

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.MutableLiveData

data class BluetoothDeviceToConnect(
    val device: BluetoothDevice,
    val isConnected: MutableLiveData<Boolean>
)