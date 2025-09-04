package com.gandor.mobile_locator.layers.data.constants

object ConstantStrings {
    const val PERMISSION_REQUIRED: String = "Permission required"
    const val PERMISSION_REQUIRED_DENIED: String = "Required permissions denied"
    const val PERMISSION_REQUIRED_LOCATION_MESSAGE: String =
        "Location permission was permanently denied. Please enable it manually in app settings. Enable also 'precise' location."
    const val OPEN_SETTINGS: String = "Open Settings"
    const val CANCEL: String = "Cancel"
    const val ALLOW: String = "Allow"
    const val NO: String = "No"
    const val GOOGLE_OPEN_LOCATION_URI: String = "https://www.google.com/maps/search/?api=1&query="
    const val NETWORK_FAIL_NO_MESSAGE = "NetworkFail has no error message please raise issue with developer"
    const val ENABLE_BACKGROUND_LOCATION = "Background location streaming"
    const val BACKGROUND_LOCATION_PERMISSION = "Background Location Permission"
    const val BACKGROUND_LOCATION_PERMISSION_DENIED = "Background location was denied on first ask. Manually allow in settings."
    const val BACKGROUND_LOCATION_PERMISSION_MESSAGE = "To track your location even when the app is closed, please allow background location access."

    object RegistrationConstants {
        const val REGISTER: String = "Register"
        const val REGISTER_SUCCESS = "Registration was successful"
    }

    object CoordinatesConstants {
        const val EMIT_LOCATION = "Emit location"
        const val OPEN_IN_GOOGLE_MAPS = "Open In Google Maps"
        const val YOU_ARE_HERE = "You Are Here"
    }

    object ButtonTextConstants {
        const val SUBMIT: String = "Submit"
        const val SUCCESS: String = "Success"
        const val ERROR: String = "Error"
        const val OK: String = "Ok"
    }

    enum class PlaceHolderEnum(val value: String) {
        USERNAME("username"),
        PASSWORD("password"),
        EMAIL("email")
        ;
    }
}
