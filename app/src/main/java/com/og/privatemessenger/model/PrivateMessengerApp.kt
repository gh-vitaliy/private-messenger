package com.og.privatemessenger.model

import android.app.Application
import com.og.privatemessenger.model.di.components.AppComponent
import com.og.privatemessenger.model.di.components.DaggerAppComponent

class PrivateMessengerApp : Application() {

    val appComponent: AppComponent = DaggerAppComponent.create()

    init {
        INSTANCE = this
    }

    override fun onCreate() {
        appComponent.inject(this)
        super.onCreate()

    }

    companion object {
        var INSTANCE: PrivateMessengerApp? = null

        fun get(): PrivateMessengerApp {
            return INSTANCE!!
        }
    }
}