package com.gandor.mobile_locator.layers.ui.composables

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.Dp

@Composable
fun CopyCoordinates(latitude: String, longitude: String, iconSize: Dp) {
    val clipboardManager = LocalClipboardManager.current

    IconButton(onClick = {
        clipboardManager.setText(AnnotatedString("$latitude, $longitude"))
    },
        modifier = Modifier
            .size(iconSize)
    ) {
        Icon(
            imageVector = Icons.Filled.ContentCopy,
            contentDescription = "Copy Latitude"
        )
    }
}