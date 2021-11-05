package com.og.privatemessenger.model.repository

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.MutableLiveData
import com.og.privatemessenger.model.FoundBluetoothDeviceBroadCastReceiver
import javax.inject.Inject

class BluetoothDeviceListRepository
@Inject constructor(private val foundBluetoothDeviceBroadCastReceiver: FoundBluetoothDeviceBroadCastReceiver) {

    var foundedDevicesList: MutableLiveData<List<BluetoothDevice>> = MutableLiveData()
    fun fetchFoundedDevices() {
        foundedDevicesList.postValue(foundBluetoothDeviceBroadCastReceiver.foundedDevices.value)
    }

}