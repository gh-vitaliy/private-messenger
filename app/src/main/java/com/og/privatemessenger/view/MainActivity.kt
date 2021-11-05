package com.og.privatemessenger.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.og.privatemessenger.R

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val fragment = FoundedDevicesListFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, fragment)
            .commit()

    }
}