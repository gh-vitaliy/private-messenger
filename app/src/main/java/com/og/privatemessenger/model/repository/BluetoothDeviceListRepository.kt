package com.og.privatemessenger.model.repository

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.MutableLiveData
import com.og.privatemessenger.model.FoundBluetoothDeviceBroadCastReceiver
import javax.inject.Inject

private const val TAG = "BluetoothDeviceListRepository"

class BluetoothDeviceListRepository
@Inject constructor(foundBluetoothDeviceBroadCastReceiver: FoundBluetoothDeviceBroadCastReceiver) {

    var foundedDevicesList: MutableLiveData<List<BluetoothDevice>> =
        foundBluetoothDeviceBroadCastReceiver.deviceListAsLiveData

}