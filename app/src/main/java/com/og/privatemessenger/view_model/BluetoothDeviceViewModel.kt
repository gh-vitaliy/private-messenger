package com.og.privatemessenger.view_model

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.text.BoringLayout
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.og.privatemessenger.model.repository.BluetoothDeviceRepository
import kotlinx.coroutines.*
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

class BluetoothDeviceViewModel(
    private val bluetoothDeviceRepository: BluetoothDeviceRepository,
    private val context: Context
) :
    BaseObservable() {

    var bluetoothDevice: BluetoothDevice? = null
        set(value) {
            field = value
            notifyChange()
        }

    @get:Bindable
    val deviceName: String
        get() = bluetoothDevice?.name.toString()

    @get:Bindable
    var isLoading: Boolean = false
        set(value) {
            notifyChange()
            field = value
        }

    @get:Bindable
    var isConnected: Boolean = false
        set(value) {
            notifyChange()
            field = value
        }

    fun onClick() {
        CoroutineScope(Job() + Dispatchers.Default).launch {
            try {
                isLoading = true
                bluetoothDeviceRepository.connect(bluetoothDevice!!)
                isConnected = true
            } catch (ioe: IOException) {
                isConnected = false
                withContext(Job() + Dispatchers.Main) {
                    Toast.makeText(context, "Cant connect to $deviceName", Toast.LENGTH_SHORT)
                        .show()
                }
            } finally {
                isLoading = false
            }
        }
    }

}