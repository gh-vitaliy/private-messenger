package com.og.privatemessenger.model.di.components

import com.og.privatemessenger.model.PrivateMessengerApp
import com.og.privatemessenger.model.di.modules.AppModule
import com.og.privatemessenger.model.di.modules.BluetoothModule
import dagger.Component

@Component(modules = [AppModule::class, BluetoothModule::class])
interface AppComponent {
    fun inject(privateMessengerApp: PrivateMessengerApp)
}