package com.gandor.mobile_locator.layers.data.retrofit.services.models

data class User(
    val username: String? = "",
    val password: String? = "",
    val androidId: String? = "",
    val email: String? = "",
    val coordinateId: String? = "",
    val registeredContacts: List<String>? = listOf(""),
    val coordinates: Coordinates? = Coordinates(),
    val isLoggedIn: Boolean = false
)
