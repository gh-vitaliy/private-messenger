package com.og.privatemessenger.view_model

import android.bluetooth.BluetoothDevice
import android.widget.Toast
import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.og.privatemessenger.R
import com.og.privatemessenger.model.PrivateMessengerApp
import com.og.privatemessenger.model.repository.BluetoothDeviceRepository
import com.og.privatemessenger.model.util.ObservableViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

private const val TAG = "BluetoothDeviceViewModel"

class BluetoothDeviceViewModel(private val bluetoothDeviceRepository: BluetoothDeviceRepository) :
    ObservableViewModel() {

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
        viewModelScope.launch(Job() + Dispatchers.Default) {
            try {
                isLoading = true
                bluetoothDeviceRepository.connect(bluetoothDevice!!)
                isConnected = true
            } catch (ioe: IOException) {
                isConnected = false
                withContext(Job() + Dispatchers.Main) {
                    Toast.makeText(
                        PrivateMessengerApp.INSTANCE?.applicationContext,
                        R.string.unable_to_connect,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } finally {
                isLoading = false
            }
        }
    }

}