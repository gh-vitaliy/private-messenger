package com.og.privatemessenger.view_model

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.MutableLiveData
import com.og.privatemessenger.model.entity.BluetoothDeviceToConnect
import com.og.privatemessenger.model.repository.BluetoothDeviceListRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BluetoothDeviceListViewModel
@Inject constructor(bluetoothDeviceListRepository: BluetoothDeviceListRepository) {

    val deviceList: MutableLiveData<Set<BluetoothDeviceToConnect>> =
        bluetoothDeviceListRepository.foundedDevicesList

}