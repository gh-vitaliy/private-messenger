package com.og.privatemessenger.model.broadcast_receiver

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.og.privatemessenger.model.bluetooth.BluetoothServerThread
import com.og.privatemessenger.model.di.components.DaggerAppComponent
import com.og.privatemessenger.model.entity.BluetoothDeviceToConnect
import com.og.privatemessenger.model.util.Constants.BLUETOOTH_DEVICE_NAME_TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val ACTION_FOUND = BluetoothDevice.ACTION_FOUND
private const val TAG = "FoundBluetoothDeviceBroadCastReceiver"


class FoundBluetoothDeviceBroadCastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var bluetoothAdapter: BluetoothAdapter

    init {
        DaggerAppComponent.create().inject(this)
    }

    val actionFoundIntentFilter = IntentFilter(BluetoothDevice.ACTION_FOUND)
    private val deviceList: MutableSet<BluetoothDeviceToConnect> = mutableSetOf()
    val deviceListAsLiveData: MutableLiveData<Set<BluetoothDeviceToConnect>> = MutableLiveData()

    override fun onReceive(context: Context?, intent: Intent) {
        if (intent.action == ACTION_FOUND) {
            val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
            Log.d(TAG, "${device?.name ?: "Empty name"} ${device?.address} founded")
            device?.let { foundedDevice ->
                //add to list devices which has invisible app TAG on bluetooth adapter name
                if (foundedDevice.name != null
                    && foundedDevice.name?.endsWith(BLUETOOTH_DEVICE_NAME_TAG) == true
                    && !deviceList.any { it.device.address == foundedDevice.address }
                ) {
                    val serverThread = BluetoothServerThread(bluetoothAdapter, foundedDevice)
                    CoroutineScope(Job() + Dispatchers.Default).launch {
                        //start server thread for all founded device which has the app
                        deviceList
                            .add(BluetoothDeviceToConnect(foundedDevice, serverThread.isConnected))
                        deviceListAsLiveData.postValue(deviceList)
                        serverThread.run()
                    }
                }
            }
        }
    }

    companion object {
        private var INSTANCE: FoundBluetoothDeviceBroadCastReceiver? = null

        private fun newInstance(): FoundBluetoothDeviceBroadCastReceiver {
            INSTANCE = FoundBluetoothDeviceBroadCastReceiver()
            return INSTANCE!!
        }

        fun get(): FoundBluetoothDeviceBroadCastReceiver {
            return INSTANCE ?: newInstance()
        }
    }


}