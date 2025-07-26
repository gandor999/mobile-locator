package com.gandor.mobile_locator.layers.data.managers

import android.app.Activity
import android.content.Context
import org.osmdroid.config.Configuration

class InitializeManager() {
    fun initializeOpenStreetMapConfigs(localActivity: Activity) {
        val sharedPrefs = localActivity.getSharedPreferences("osmdroid", Context.MODE_PRIVATE)

        Configuration.getInstance().load(
            localActivity,
            sharedPrefs
        )
    }
}