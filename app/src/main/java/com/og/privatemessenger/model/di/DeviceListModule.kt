package com.og.privatemessenger.model.di

import android.content.BroadcastReceiver
import com.og.privatemessenger.model.FoundBluetoothDeviceBroadCastReceiver
import com.og.privatemessenger.model.repository.BluetoothDeviceListRepository
import com.og.privatemessenger.view_model.BluetoothDeviceListViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Inject

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