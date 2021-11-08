package com.og.privatemessenger.view

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.og.privatemessenger.R
import com.og.privatemessenger.databinding.DeviceListItemBinding
import com.og.privatemessenger.model.broadcast_receiver.FoundBluetoothDeviceBroadCastReceiver
import com.og.privatemessenger.model.di.components.DaggerDeviceListFragmentComponent
import com.og.privatemessenger.view_model.BluetoothDeviceListViewModel
import com.og.privatemessenger.view_model.BluetoothDeviceViewModel
import javax.inject.Inject

class FoundedDevicesListFragment : Fragment() {

    @Inject
    lateinit var foundBluetoothDeviceBroadcastReceiver: FoundBluetoothDeviceBroadCastReceiver

    @Inject
    lateinit var bluetoothDeviceListViewModel: BluetoothDeviceListViewModel

    @Inject
    lateinit var bluetoothAdapter: BluetoothAdapter

    private lateinit var deviceListRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DaggerDeviceListFragmentComponent.create().inject(this)
        val view = inflater.inflate(R.layout.fragment_founded_devices_list, container, false)
        deviceListRecyclerView = view.findViewById(R.id.devices_list_recycler_view)

        deviceListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        return view
    }

    override fun onStart() {
        super.onStart()

        activity?.registerReceiver(
            foundBluetoothDeviceBroadcastReceiver,
            foundBluetoothDeviceBroadcastReceiver.actionFoundIntentFilter
        )
        if (!bluetoothAdapter.isDiscovering)
            bluetoothAdapter.startDiscovery()

        setObservers()
    }

    override fun onDestroy() {
        super.onDestroy()
        bluetoothAdapter.cancelDiscovery()
        activity?.unregisterReceiver(foundBluetoothDeviceBroadcastReceiver)
        FoundBluetoothDeviceBroadCastReceiver.newInstance()
    }

    private fun setObservers() {
        bluetoothDeviceListViewModel.deviceList.observe(viewLifecycleOwner) { devices ->
            devices?.let { deviceListRecyclerView.adapter = DeviceAdapter(it) }
        }
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
                binding.viewModel?.bluetoothDevice = device
            }
        }
    }

}