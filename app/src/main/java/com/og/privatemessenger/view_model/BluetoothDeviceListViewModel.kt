package com.og.privatemessenger.view_model

import android.bluetooth.BluetoothDevice
import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.og.privatemessenger.model.repository.BluetoothDeviceListRepository
import javax.inject.Inject

class BluetoothDeviceListViewModel
@Inject constructor(private val bluetoothDeviceListRepository: BluetoothDeviceListRepository) :
    BaseObservable() {

    val deviceList: MutableLiveData<List<BluetoothDevice>> =
        bluetoothDeviceListRepository.foundedDevicesList

    init {
        bluetoothDeviceListRepository.fetchFoundedDevices()
    }

}