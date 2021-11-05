package com.og.privatemessenger.model.di

import android.bluetooth.BluetoothAdapter
import com.og.privatemessenger.model.FoundBluetoothDeviceBroadCastReceiver
import dagger.Module
import dagger.Provides

@Module
class BluetoothModule {

    @Provides
    fun provideBluetoothAdapter(): BluetoothAdapter {
        return BluetoothAdapter.getDefaultAdapter()
    }

    @Provides
    fun provideFoundDeviceBroadcastReceiver(): FoundBluetoothDeviceBroadCastReceiver {
        return FoundBluetoothDeviceBroadCastReceiver()
    }

}