package com.gandor.mobile_locator.layers.ui

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.gandor.mobile_locator.MainActivity
import com.gandor.mobile_locator.layers.ui.composables.MainComposable
import com.gandor.mobile_locator.layers.ui.theme.Mobile_locatorTheme

@RequiresApi(Build.VERSION_CODES.Q)
class MainUi(private val mainActivity: MainActivity): Ui {
    override fun showUi() {
        mainActivity.setContent {
            Mobile_locatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainComposable()
                }
            }
        }
    }
}