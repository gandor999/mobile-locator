package com.gandor.mobile_locator.layers.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.gandor.mobile_locator.layers.ui.viewmodels.MainViewModel

@Composable
fun MainCoordinatesPanel(
    mainViewModel: MainViewModel
) {
    val mainCoordinatePanelState by mainViewModel.mainCoordinatePanelState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.75f)
//                .border(2.dp, Color.Red)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        if (mainCoordinatePanelState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center // 👈 centers both vertically and horizontally
            ) {
                CircularProgressIndicator()
            }
        } else {
            SelectionContainer {
                Column {
                    val textStyle = MaterialTheme.typography.bodyLarge
                    val fontSize = textStyle.fontSize

                    val iconSizeDp = with(LocalDensity.current) { fontSize.toDp() }

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Latitude: ${mainCoordinatePanelState.latitude}")
                        CopyCoordinatesLatitude(mainCoordinatePanelState.latitude.toString(), iconSizeDp)
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Longitude: ${mainCoordinatePanelState.longitude}")
                        CopyCoordinatesLongitude(mainCoordinatePanelState.longitude.toString(), iconSizeDp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OpenStreetMapView(
                lat = mainCoordinatePanelState.latitude,
                lon = mainCoordinatePanelState.longitude
            )
        }
    }
}