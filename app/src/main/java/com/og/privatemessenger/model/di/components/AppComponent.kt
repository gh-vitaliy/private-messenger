package com.og.privatemessenger.model.di.components

import com.og.privatemessenger.model.PrivateMessengerApp
import com.og.privatemessenger.model.broadcast_receiver.FoundBluetoothDeviceBroadCastReceiver
import com.og.privatemessenger.model.di.modules.AppModule
import com.og.privatemessenger.model.di.modules.BluetoothModule
import com.og.privatemessenger.view.MainActivity
import dagger.Component

@Component(modules = [BluetoothModule::class, AppModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(privateMessengerApp: PrivateMessengerApp)
    fun inject(bluetoothDeviceBroadCastReceiver: FoundBluetoothDeviceBroadCastReceiver)
}