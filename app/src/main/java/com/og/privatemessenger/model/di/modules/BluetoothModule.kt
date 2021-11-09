package com.og.privatemessenger.model.di.modules

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import com.og.privatemessenger.model.PrivateMessengerApp
import com.og.privatemessenger.model.bluetooth.BluetoothServerThread
import com.og.privatemessenger.model.broadcast_receiver.FoundBluetoothDeviceBroadCastReceiver
import dagger.Module
import dagger.Provides

@Module
class BluetoothModule {


    @Provides
    fun provideBluetoothAdapter(): BluetoothAdapter {
        return (PrivateMessengerApp.get().applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
    }

    @Provides
    fun provideBluetoothServerThread(bluetoothAdapter: BluetoothAdapter): BluetoothServerThread {
        return BluetoothServerThread(bluetoothAdapter)
    }

    @Provides
    fun provideFoundDeviceBroadcastReceiver(): FoundBluetoothDeviceBroadCastReceiver {
        return FoundBluetoothDeviceBroadCastReceiver.get()
    }

}