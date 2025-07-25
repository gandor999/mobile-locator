package com.gandor.mobile_locator.managers

import android.content.Context
import com.gandor.mobile_locator.MainActivity
import org.osmdroid.config.Configuration

class InitializeManager(private val mainActivity: MainActivity) {
    fun initializeOpenStreetMapConfigs() {
        val sharedPrefs = mainActivity.getSharedPreferences("osmdroid", Context.MODE_PRIVATE)

        Configuration.getInstance().load(
            mainActivity,
            sharedPrefs
        )
    }
}