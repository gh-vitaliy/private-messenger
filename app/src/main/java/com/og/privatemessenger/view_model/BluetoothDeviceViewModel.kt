package com.og.privatemessenger.view_model

import android.widget.Toast
import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.og.privatemessenger.R
import com.og.privatemessenger.model.PrivateMessengerApp
import com.og.privatemessenger.model.entity.BluetoothDeviceToConnect
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

    var bluetoothDevice: BluetoothDeviceToConnect? = null
        set(value) {
            field = value
            notifyChange()
        }


    @get:Bindable
    val deviceName: String
        get() = bluetoothDevice?.device?.name.toString()

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

    @get:Bindable
    val isConnectedByServer: Boolean
        get() {
            notifyChange()
            return bluetoothDevice?.isConnected?.value == true
        }


    fun onClick() {
        val context = PrivateMessengerApp.INSTANCE?.applicationContext
        if (!isConnected || !isConnectedByServer)
            viewModelScope.launch(Job() + Dispatchers.Default) {
                try {
                    isLoading = true
                    bluetoothDeviceRepository.connect(bluetoothDevice!!.device)
                    isConnected = true
                } catch (ioe: IOException) {
                    withContext(Job() + Dispatchers.Main) {
                        Toast.makeText(context, R.string.unable_to_connect, Toast.LENGTH_SHORT)
                            .show()
                    }
                } finally {
                    isLoading = false
                }
            }
        else
            Toast.makeText(context, R.string.you_already_connected, Toast.LENGTH_SHORT).show()
    }

}