package com.og.privatemessenger.model

import android.app.Application
import com.og.privatemessenger.model.bluetooth.BluetoothServerThread
import com.og.privatemessenger.model.di.components.DaggerAppComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrivateMessengerApp : Application() {
    @Inject
    lateinit var bluetoothServerThread: BluetoothServerThread

    override fun onCreate() {
        DaggerAppComponent.create().inject(this)
        super.onCreate()
        //start async bcs it block main thread
        CoroutineScope(Job() + Dispatchers.Default).launch {
            bluetoothServerThread.run()
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        bluetoothServerThread.cancel()
    }
}