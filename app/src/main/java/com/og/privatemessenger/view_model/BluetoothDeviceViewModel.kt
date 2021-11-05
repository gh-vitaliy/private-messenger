package com.og.privatemessenger.view_model

import android.bluetooth.BluetoothDevice
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class BluetoothDeviceViewModel : BaseObservable() {

    var bluetoothDevice: BluetoothDevice? = null
        set(value) {
            field = value
            notifyChange()
        }

    @get:Bindable
    val deviceName: String?
        get() = bluetoothDevice?.name

}