package com.og.privatemessenger.view

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.og.privatemessenger.R
import com.og.privatemessenger.databinding.DeviceListItemBinding
import com.og.privatemessenger.model.di.components.DaggerBluetoothDeviceListComponent
import com.og.privatemessenger.model.entity.BluetoothDeviceToConnect
import com.og.privatemessenger.model.repository.BluetoothDeviceRepository
import com.og.privatemessenger.view_model.BluetoothDeviceListViewModel
import com.og.privatemessenger.view_model.BluetoothDeviceViewModel
import javax.inject.Inject

private const val TAG = "FoundedDevicesListFragment"

class FoundedDevicesListFragment : Fragment() {

    @Inject
    lateinit var bluetoothDeviceListViewModel: BluetoothDeviceListViewModel

    @Inject
    lateinit var bluetoothAdapter: BluetoothAdapter

    @Inject
    lateinit var bluetoothDeviceRepository: BluetoothDeviceRepository

    private lateinit var deviceListRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerBluetoothDeviceListComponent.create().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_founded_devices_list, container, false)
        setHasOptionsMenu(true)
        deviceListRecyclerView = view.findViewById(R.id.devices_list_recycler_view)

        Log.d(TAG, "bonded ${bluetoothAdapter.bondedDevices.map { it.name }}")

        deviceListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = DeviceAdapter()
        }

        return view
    }

    override fun onStart() {
        super.onStart()

        setDeviceDiscoverable()
        startDiscovery()
        setObservers()
    }

    override fun onStop() {
        super.onStop()
        bluetoothDeviceListViewModel.deviceList.removeObservers(viewLifecycleOwner)
    }

    override fun onDestroy() {
        bluetoothAdapter.cancelDiscovery()
        deviceListRecyclerView.adapter = null
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        activity?.title = "Founded devices"
        inflater.inflate(R.menu.device_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.start_discovery -> startDiscovery()
            }
            true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setObservers() {
        bluetoothDeviceListViewModel.deviceList.observe(viewLifecycleOwner) { devices ->
            devices?.let {
                (deviceListRecyclerView.adapter as DeviceAdapter).submitList(devices.toList())
            }
        }
    }

    private fun setDeviceDiscoverable() {
        if (bluetoothAdapter.scanMode != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            val discoverableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
            startActivity(discoverableIntent)
        }
    }

    private fun startDiscovery() {
        if (!bluetoothAdapter.isDiscovering) {
            bluetoothAdapter.startDiscovery()
            Log.d(TAG, "Discovery starting")
        }
    }

    inner class DeviceAdapter :
        ListAdapter<BluetoothDeviceToConnect, DeviceAdapter.DeviceViewHolder>(ITEM_CALLBACK) {
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
            val device = getItem(position)
            holder.bind(device)
        }

        inner class DeviceViewHolder(private val binding: DeviceListItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
            init {
                binding.viewModel = BluetoothDeviceViewModel(bluetoothDeviceRepository)
            }

            fun bind(device: BluetoothDeviceToConnect) {
                binding.viewModel?.bluetoothDevice = device
            }
        }

    }

    companion object {
        val ITEM_CALLBACK = object : DiffUtil.ItemCallback<BluetoothDeviceToConnect>() {
            override fun areItemsTheSame(
                oldItem: BluetoothDeviceToConnect,
                newItem: BluetoothDeviceToConnect
            ): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: BluetoothDeviceToConnect,
                newItem: BluetoothDeviceToConnect
            ): Boolean {
                return oldItem.device == newItem.device
            }
        }
    }
}