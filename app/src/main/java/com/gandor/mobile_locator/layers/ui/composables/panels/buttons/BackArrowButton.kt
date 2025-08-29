package com.gandor.mobile_locator.layers.ui.composables.panels.buttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@Composable
fun BackArrowButton(onBackClick: () -> Unit) {
    IconButton(onClick = onBackClick) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back"
        )
    }
}