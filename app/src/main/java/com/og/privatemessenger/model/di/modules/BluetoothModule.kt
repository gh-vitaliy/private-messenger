package com.og.privatemessenger.model.di.modules

import android.bluetooth.BluetoothAdapter
import com.og.privatemessenger.model.bluetooth.BluetoothService
import com.og.privatemessenger.model.broadcast_receiver.FoundBluetoothDeviceBroadCastReceiver
import dagger.Module
import dagger.Provides

@Module
class BluetoothModule {


    @Provides
    fun provideBluetoothAdapter(): BluetoothAdapter {
        return BluetoothAdapter.getDefaultAdapter()
    }

    @Provides
    fun provideBluetoothService(bluetoothAdapter: BluetoothAdapter): BluetoothService {
        return BluetoothService(bluetoothAdapter)
    }

    @Provides
    fun provideBluetoothServerThread(bluetoothService: BluetoothService): BluetoothService.BluetoothServerThread {
        return bluetoothService.BluetoothServerThread()
    }



    @Provides
    fun provideFoundDeviceBroadcastReceiver(): FoundBluetoothDeviceBroadCastReceiver {
        return FoundBluetoothDeviceBroadCastReceiver.get()
    }

}