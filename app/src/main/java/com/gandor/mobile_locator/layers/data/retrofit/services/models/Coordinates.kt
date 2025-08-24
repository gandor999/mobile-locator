package com.gandor.mobile_locator.layers.data.retrofit.services.models

import kotlinx.serialization.Serializable

@Serializable
data class Coordinates(
    val usernameId: String? = "",
    val latitude: Double? = 0.00,
    val longitude: Double? = 0.00
)
