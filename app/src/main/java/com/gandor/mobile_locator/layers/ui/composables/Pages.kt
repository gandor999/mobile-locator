package com.gandor.mobile_locator.layers.ui.composables

import kotlinx.serialization.Serializable

@Serializable
sealed interface Page

@Serializable
object RegisterPage: Page

@Serializable
object CoordinatesPage: Page

@Serializable
object SettingsPage: Page

@Serializable
object LoginPage: Page
