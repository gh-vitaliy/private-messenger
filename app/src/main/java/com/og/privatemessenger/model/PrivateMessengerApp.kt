package com.og.privatemessenger.model

import android.app.Application
import com.og.privatemessenger.model.bluetooth.BluetoothServerThread
import com.og.privatemessenger.model.di.components.DaggerAppComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class PrivateMessengerApp : Application() {

    val appComponent = DaggerAppComponent.create()

    @Inject
    lateinit var bluetoothServerThread: BluetoothServerThread

    init {
        INSTANCE = this
    }

    override fun onCreate() {
        appComponent.inject(this)
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

    companion object {
        var INSTANCE: PrivateMessengerApp? = null

        fun get(): PrivateMessengerApp {
            return INSTANCE!!
        }
    }
}