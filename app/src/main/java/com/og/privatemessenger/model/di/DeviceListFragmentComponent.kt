package com.og.privatemessenger.model.di

import com.og.privatemessenger.view.FoundedDevicesListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [BluetoothModule::class, DeviceListModule::class])
interface DeviceListFragmentComponent {
    fun inject(userListDevicesListFragment: FoundedDevicesListFragment)
}