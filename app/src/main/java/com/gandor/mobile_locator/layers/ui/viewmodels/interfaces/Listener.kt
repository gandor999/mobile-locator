package com.gandor.mobile_locator.layers.ui.viewmodels.interfaces

import android.content.Context
import com.gandor.mobile_locator.layers.data.event.Event

interface Listener {
    fun consumeEvent(event: Event, context: Context?)
}