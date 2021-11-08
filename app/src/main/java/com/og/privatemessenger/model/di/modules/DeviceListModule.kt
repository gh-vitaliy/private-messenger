package com.og.privatemessenger.model.di.modules

import com.og.privatemessenger.model.broadcast_receiver.FoundBluetoothDeviceBroadCastReceiver
import com.og.privatemessenger.model.repository.BluetoothDeviceListRepository
import com.og.privatemessenger.view_model.BluetoothDeviceListViewModel
import dagger.Module
import dagger.Provides

@Module
class DeviceListModule {


    @Provides
    fun provideBluetoothDeviceListRepository(bluetoothDeviceBroadCastReceiver: FoundBluetoothDeviceBroadCastReceiver): BluetoothDeviceListRepository {
        return BluetoothDeviceListRepository(bluetoothDeviceBroadCastReceiver)
    }

    @Provides
    fun provideBluetoothDeviceListViewModel(bluetoothDeviceListRepository: BluetoothDeviceListRepository): BluetoothDeviceListViewModel {
        return BluetoothDeviceListViewModel(bluetoothDeviceListRepository)
    }

}