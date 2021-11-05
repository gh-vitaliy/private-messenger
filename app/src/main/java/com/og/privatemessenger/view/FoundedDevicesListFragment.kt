package com.og.privatemessenger.view

import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.og.privatemessenger.R
import com.og.privatemessenger.databinding.DeviceListItemBinding
import com.og.privatemessenger.view_model.BluetoothDeviceViewModel

class FoundedDevicesListFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_founded_devices_list, container, false)



        return view
    }

    inner class DeviceAdapter(private val devices: List<BluetoothDevice>) :
        RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
            val binding = DataBindingUtil.inflate<DeviceListItemBinding>(
                layoutInflater,
                R.layout.device_list_item,
                parent,
                false
            )
            return DeviceViewHolder(binding)
        }

        override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
            holder.bind(devices[position])
        }

        override fun getItemCount(): Int = devices.size

        inner class DeviceViewHolder(private val binding: DeviceListItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
            init {
                binding.viewModel = BluetoothDeviceViewModel()
            }

            fun bind(device: BluetoothDevice) {
                binding.viewModel.bluetoothDevice = device
            }
        }
    }

}