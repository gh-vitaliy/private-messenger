package com.og.privatemessenger.model.repository

import androidx.lifecycle.MutableLiveData
import com.og.privatemessenger.model.broadcast_receiver.FoundBluetoothDeviceBroadCastReceiver
import com.og.privatemessenger.model.entity.BluetoothDeviceToConnect
import javax.inject.Inject

private const val TAG = "BluetoothDeviceListRepository"

class BluetoothDeviceListRepository
@Inject constructor(foundBluetoothDeviceBroadCastReceiver: FoundBluetoothDeviceBroadCastReceiver) {

    var foundedDevicesList: MutableLiveData<Set<BluetoothDeviceToConnect>> =
        foundBluetoothDeviceBroadCastReceiver.deviceListAsLiveData

}