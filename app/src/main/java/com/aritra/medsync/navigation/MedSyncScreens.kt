package com.aritra.medsync.navigation

sealed class MedSyncScreens(val name: String) {

    object Splash : MedSyncScreens("Splash")
    object Login : MedSyncScreens("Login")
    object Signup : MedSyncScreens("Signup")
    object Home : MedSyncScreens("Home")
}
