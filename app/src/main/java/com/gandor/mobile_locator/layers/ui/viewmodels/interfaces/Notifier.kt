package com.gandor.mobile_locator.layers.ui.viewmodels.interfaces

import android.content.Context

interface Notifier {
    fun registerListener(listener: Listener)

    fun notifyListeners(context: Context?)
}