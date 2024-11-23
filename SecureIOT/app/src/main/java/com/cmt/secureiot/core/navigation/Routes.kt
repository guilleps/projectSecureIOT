package com.cmt.secureiot.core.navigation

sealed class Routes(val route: String) {
    object HomeScreen : Routes("home")
    object LoginScreen : Routes("login")
    object PanelScreen : Routes("panel")
    object DoorScreen : Routes("door")
    object LightScreen : Routes("light")
    object AlarmScreen : Routes("alarm")
    object CamScreen : Routes("cam")
    object ControlScreen : Routes("control")
}