package com.og.privatemessenger.view

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.og.privatemessenger.R
import com.og.privatemessenger.model.PrivateMessengerApp
import com.og.privatemessenger.model.broadcast_receiver.FoundBluetoothDeviceBroadCastReceiver
import com.og.privatemessenger.model.util.Constants.BLUETOOTH_DEVICE_NAME_TAG
import javax.inject.Inject

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {


    @Inject
    lateinit var bluetoothAdapter: BluetoothAdapter

    @Inject
    lateinit var foundBluetoothDeviceBroadcastReceiver: FoundBluetoothDeviceBroadCastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        PrivateMessengerApp.INSTANCE?.appComponent?.inject(this)
        super.onCreate(savedInstanceState)
        //add invisible symbols to help other devices to define that user has the app
        bluetoothAdapter.apply {
            if (!this.name.endsWith(BLUETOOTH_DEVICE_NAME_TAG))
                this.name = "${this.name} $BLUETOOTH_DEVICE_NAME_TAG"
        }
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        registerReceiver(
            foundBluetoothDeviceBroadcastReceiver,
            foundBluetoothDeviceBroadcastReceiver.actionFoundIntentFilter
        )

        showPermissionsDialog()

        val fragment = FoundedDevicesListFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(foundBluetoothDeviceBroadcastReceiver)
    }


    private fun showPermissionsDialog() {
        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        if (checkSelfPermission(permissions[0]) != PackageManager.PERMISSION_GRANTED)
            MaterialAlertDialogBuilder(this).apply {
                setTitle(R.string.bluetooth_permission_title)
                setMessage(R.string.bluetooth_permission_message)
                setNeutralButton(R.string.ok) { dialog, _ ->
                    dialog.dismiss()
                    requestPermissions(permissions, 0)
                }
                show()
            }
    }


}