package com.og.privatemessenger.model.di.components

import com.og.privatemessenger.model.di.modules.BluetoothModule
import com.og.privatemessenger.model.di.modules.DeviceListModule
import com.og.privatemessenger.view.FoundedDevicesListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [BluetoothModule::class, DeviceListModule::class])
interface DeviceListFragmentComponent {
    fun inject(userListDevicesListFragment: FoundedDevicesListFragment)
}