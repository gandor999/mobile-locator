package com.gandor.mobile_locator.layers.ui.composables.panels.coordinates

import android.annotation.SuppressLint
import android.app.Activity
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
import com.gandor.mobile_locator.layers.ui.viewmodels.CoordinatesViewModel

@SuppressLint("ContextCastToActivity")
@Composable
fun ActionButtonsPanel(
    coordinatesViewModel: CoordinatesViewModel
) {
    val activity = LocalContext.current as? Activity

    LazyColumn(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End,
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Button(
                onClick = {
                    if (activity != null) {
                        coordinatesViewModel.showCoordinates(activity)
                    }
                }) {
                Text(text = ConstantStrings.CoordinatesConstants.SHOW_COORDINATES)
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