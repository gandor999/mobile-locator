package com.gandor.mobile_locator.layers.data.constants

object ConstantStrings {
    const val PERMISSION_REQUIRED: String = "Permission required"
    const val PERMISSION_REQUIRED_DENIED: String = "Required permissions denied"
    const val PERMISSION_REQUIRED_LOCATION_MESSAGE: String =
        "Location permission was permanently denied. Please enable it manually in app settings. Enable also 'precise' location."
    const val OPEN_SETTINGS: String = "Open Settings"
    const val CANCEL: String = "Cancel"
    const val GOOGLE_OPEN_LOCATION_URI: String = "https://www.google.com/maps/search/?api=1&query="
    const val REGISTER: String = "Register"
    const val SUBMIT: String = "Submit"

    enum class PlaceHolderEnum(val value: String) {
        USERNAME("username"),
        PASSWORD("password"),
        EMAIL("email")
        ;
    }
}
