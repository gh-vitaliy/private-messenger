package com.og.privatemessenger.view

import android.bluetooth.BluetoothAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.og.privatemessenger.R
import com.og.privatemessenger.model.PrivateMessengerApp
import com.og.privatemessenger.model.util.Constants.BLUETOOTH_DEVICE_NAME_TAG
import javax.inject.Inject

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var bluetoothAdapter: BluetoothAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        PrivateMessengerApp.INSTANCE?.appComponent?.inject(this)
        super.onCreate(savedInstanceState)

        //add invisible symbols to help other devices to define that user has the app
        bluetoothAdapter.apply {
            if (!this.name.endsWith(BLUETOOTH_DEVICE_NAME_TAG))
                this.name = "${this.name} $BLUETOOTH_DEVICE_NAME_TAG"
        }
        setContentView(R.layout.activity_main)

        val fragment = FoundedDevicesListFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, fragment)
            .commit()
    }


}