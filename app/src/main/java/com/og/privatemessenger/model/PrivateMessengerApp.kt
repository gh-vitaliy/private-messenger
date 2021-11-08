package com.og.privatemessenger.model

import android.app.Application
import android.bluetooth.BluetoothAdapter
import com.og.privatemessenger.model.bluetooth.BluetoothServerThread
import com.og.privatemessenger.model.di.components.AppComponent
import com.og.privatemessenger.model.di.components.DaggerAppComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrivateMessengerApp : Application() {

    private val appComponent: AppComponent = DaggerAppComponent.create()

    @Inject
    lateinit var bluetoothAdapter: BluetoothAdapter

    init {
        appComponent.inject(this)
        //start not blocked
        CoroutineScope(Job() + Dispatchers.Default).launch {
            BluetoothServerThread(bluetoothAdapter).run()
        }
    }


}