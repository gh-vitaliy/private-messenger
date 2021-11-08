package com.og.privatemessenger.model.repository

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.MutableLiveData
import com.og.privatemessenger.model.broadcast_receiver.FoundBluetoothDeviceBroadCastReceiver
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "BluetoothDeviceListRepository"

class BluetoothDeviceListRepository
@Inject constructor(foundBluetoothDeviceBroadCastReceiver: FoundBluetoothDeviceBroadCastReceiver) {

    var foundedDevicesList: MutableLiveData<List<BluetoothDevice>> =
        foundBluetoothDeviceBroadCastReceiver.deviceListAsLiveData

}