package com.og.privatemessenger.model.di.modules

import android.content.Context
import androidx.databinding.BaseObservable
import com.og.privatemessenger.model.PrivateMessengerApp
import com.og.privatemessenger.model.broadcast_receiver.FoundBluetoothDeviceBroadCastReceiver
import com.og.privatemessenger.model.repository.BluetoothDeviceListRepository
import com.og.privatemessenger.model.repository.BluetoothDeviceRepository
import com.og.privatemessenger.view_model.BluetoothDeviceListViewModel
import com.og.privatemessenger.view_model.BluetoothDeviceViewModel
import dagger.Module
import dagger.Provides

@Module
class DeviceListModule {

    @Provides
    fun providePrivateMessengerApp(): PrivateMessengerApp {
        return PrivateMessengerApp.get()
    }

    @Provides
    fun provideBluetoothDeviceListRepository(bluetoothDeviceBroadCastReceiver: FoundBluetoothDeviceBroadCastReceiver): BluetoothDeviceListRepository {
        return BluetoothDeviceListRepository(bluetoothDeviceBroadCastReceiver)
    }

    @Provides
    fun provideBluetoothDeviceListViewModel(bluetoothDeviceListRepository: BluetoothDeviceListRepository): BluetoothDeviceListViewModel {
        return BluetoothDeviceListViewModel(bluetoothDeviceListRepository)
    }

    @Provides
    fun provideBluetoothDeviceRepository(): BluetoothDeviceRepository {
        return BluetoothDeviceRepository()
    }

    @Provides
    fun provideBluetoothDeviceViewModel(bluetoothDeviceRepository: BluetoothDeviceRepository,context: Context): BluetoothDeviceViewModel {
        return BluetoothDeviceViewModel(bluetoothDeviceRepository,context)
    }


}