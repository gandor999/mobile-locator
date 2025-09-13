package com.gandor.mobile_locator.layers.ui.viewmodels.states

import com.gandor.mobile_locator.layers.ui.composables.BlankPage
import com.gandor.mobile_locator.layers.ui.composables.Page

data class NavigationState(
    val startDestination: Page = BlankPage
)
