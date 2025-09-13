package com.gandor.mobile_locator.layers.ui.composables.panels.coordinates

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.gandor.mobile_locator.layers.data.constants.ConstantStrings
import com.gandor.mobile_locator.layers.ui.composables.SettingsPage
import com.gandor.mobile_locator.layers.ui.viewmodels.CoordinatesViewModel

@RequiresApi(Build.VERSION_CODES.Q)
@SuppressLint("ContextCastToActivity")
@Composable
fun ActionButtonsPanel(
    coordinatesViewModel: CoordinatesViewModel
) {
    val activity = LocalContext.current as? Activity

    LazyColumn(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Button(
                onClick = {
                    coordinatesViewModel.switchPages(SettingsPage)
                }) {
                Text(text = ConstantStrings.OPEN_SETTINGS)
            }

            Button(
                onClick = {
                    if (activity != null) {
                        coordinatesViewModel.openCoordinatesWithGoogle(activity)
                    }
                }) {
                Text(text = ConstantStrings.CoordinatesConstants.OPEN_IN_GOOGLE_MAPS)
            }
        }
    }
}