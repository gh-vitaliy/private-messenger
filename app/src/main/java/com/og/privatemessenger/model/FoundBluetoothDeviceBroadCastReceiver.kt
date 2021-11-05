package com.og.privatemessenger.model

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.MutableLiveData
import javax.inject.Singleton

private const val ACTION_FOUND = BluetoothDevice.ACTION_FOUND

@Singleton
class FoundBluetoothDeviceBroadCastReceiver : BroadcastReceiver() {

    val actionFoundIntentFilter = IntentFilter(BluetoothDevice.ACTION_FOUND)
    val foundedDevices: MutableLiveData<MutableList<BluetoothDevice>> = MutableLiveData()
    override fun onReceive(context: Context?, intent: Intent) {
        if (intent.action == ACTION_FOUND) {
            val device: BluetoothDevice? =
                intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
            device?.let { foundedDevices.value?.add(it) }
        }
    }

}