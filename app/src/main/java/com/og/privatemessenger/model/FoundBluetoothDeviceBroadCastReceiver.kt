package com.og.privatemessenger.model

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.lifecycle.MutableLiveData

private const val ACTION_FOUND = BluetoothDevice.ACTION_FOUND
private const val TAG = "FoundBluetoothDeviceBroadCastReceiver"

class FoundBluetoothDeviceBroadCastReceiver : BroadcastReceiver() {

    val actionFoundIntentFilter = IntentFilter(BluetoothDevice.ACTION_FOUND)
    private val deviceList: MutableList<BluetoothDevice> = mutableListOf()
    val deviceListAsLiveData: MutableLiveData<List<BluetoothDevice>> = MutableLiveData()
    override fun onReceive(context: Context?, intent: Intent) {
        if (intent.action == ACTION_FOUND) {
            val device: BluetoothDevice? =
                intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
            Log.d(TAG, "${device?.address} founded")
            device?.let { foundedDevice ->
                foundedDevice.name?.let {
                    deviceList.add(foundedDevice)
                    deviceListAsLiveData.value = deviceList
                }
            }
        }
    }

    companion object {
        private var INSTANCE: FoundBluetoothDeviceBroadCastReceiver? = null

        fun newInstance(): FoundBluetoothDeviceBroadCastReceiver {
            INSTANCE = FoundBluetoothDeviceBroadCastReceiver()
            return INSTANCE!!
        }

        fun get(): FoundBluetoothDeviceBroadCastReceiver {
            return INSTANCE ?: newInstance()
        }
    }

}