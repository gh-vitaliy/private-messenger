package com.og.privatemessenger.model.di.components

import com.og.privatemessenger.model.bluetooth.BluetoothClientThread
import com.og.privatemessenger.model.di.modules.AppModule
import com.og.privatemessenger.model.di.modules.BluetoothModule
import com.og.privatemessenger.model.di.modules.DeviceListModule
import com.og.privatemessenger.view.FoundedDevicesListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, BluetoothModule::class, DeviceListModule::class])
interface BluetoothDeviceListComponent {
    fun inject(userListDevicesListFragment: FoundedDevicesListFragment)
    fun inject(bluetoothClientThread: BluetoothClientThread)
}