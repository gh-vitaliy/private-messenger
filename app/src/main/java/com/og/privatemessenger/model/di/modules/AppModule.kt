package com.og.privatemessenger.model.di.modules

import android.content.Context
import com.og.privatemessenger.model.PrivateMessengerApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideContext(privateMessengerApp: PrivateMessengerApp): Context {
        return privateMessengerApp.applicationContext
    }


}