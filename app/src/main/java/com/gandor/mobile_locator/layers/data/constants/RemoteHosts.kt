package com.gandor.mobile_locator.layers.data.constants

// Do this command when testing on a physical phone: adb reverse tcp:8000 tcp:8000
// Use 10.0.2.2:8080 if testing on emulator
object RemoteHosts {
//    const val MOBILE_LOCATOR_HOST: String = "http://10.0.2.2:8080/locations/"
    const val MOBILE_LOCATOR_HOST: String = "http://10.0.2.2:8080/"
//    const val MOBILE_LOCATOR_HOST: String = "http://localhost:8080/"
    const val LOCATION_ENTRYPOINT: String = "locations/"
    const val LOGIN_ENTRYPOINT: String = "auth/"
}