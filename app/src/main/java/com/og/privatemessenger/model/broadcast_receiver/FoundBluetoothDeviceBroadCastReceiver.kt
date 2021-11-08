package com.og.privatemessenger.model.broadcast_receiver

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.og.privatemessenger.model.bluetooth.BluetoothClientThread
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException

private const val ACTION_FOUND = BluetoothDevice.ACTION_FOUND
private const val TAG = "FoundBluetoothDeviceBroadCastReceiver"


class FoundBluetoothDeviceBroadCastReceiver : BroadcastReceiver() {

    val actionFoundIntentFilter = IntentFilter(BluetoothDevice.ACTION_FOUND)
    private val deviceList: MutableList<BluetoothDevice> = mutableListOf()
    val deviceListAsLiveData: MutableLiveData<List<BluetoothDevice>> = MutableLiveData()

    override fun onReceive(context: Context?, intent: Intent) {
        if (intent.action == ACTION_FOUND) {
            val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
            Log.d(TAG, "${device?.name ?: "Empty name"} ${device?.address} founded")
            device?.let { foundedDevice ->
                foundedDevice.name?.let {
                    //to show only devices which has the app
                    CoroutineScope(Job() + Dispatchers.Default).launch {
                        try {
                            BluetoothClientThread(foundedDevice).run {
                                bluetoothClientThreads.add(this)
                                run()
                            }
                            deviceList.add(foundedDevice)
                            deviceListAsLiveData.postValue(deviceList)
                        } catch (e: IOException) {
                            Log.d(TAG, "Device ${foundedDevice.name} hasn't got the app")
                        }
                    }

                }
            }
        }
    }

    companion object {
        private val bluetoothClientThreads: MutableList<BluetoothClientThread> = mutableListOf()

        private var INSTANCE: FoundBluetoothDeviceBroadCastReceiver? = null

        fun newInstance(): FoundBluetoothDeviceBroadCastReceiver {
            bluetoothClientThreads.forEach { it.cancel() }
            INSTANCE = FoundBluetoothDeviceBroadCastReceiver()
            return INSTANCE!!
        }

        fun get(): FoundBluetoothDeviceBroadCastReceiver {
            return INSTANCE ?: newInstance()
        }
    }


}