package com.gandor.mobile_locator

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import com.gandor.mobile_locator.layers.data.config.MainActivityConfigurator
import com.gandor.mobile_locator.layers.ui.MainUi

@RequiresApi(Build.VERSION_CODES.Q)
class MainActivity : ComponentActivity() {
    private val mainActivityConfigurator = MainActivityConfigurator(this)
    private val mainUi = MainUi(this)
    lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivityConfigurator.initializeConfiguration()
        mainUi.showUi()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivityConfigurator.handleClosing()
    }
}