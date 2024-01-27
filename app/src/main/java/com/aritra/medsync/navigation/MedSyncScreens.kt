package com.aritra.medsync.navigation

import com.aritra.medsync.utils.Constants

sealed class MedSyncScreens(val name: String) {

    object Splash : MedSyncScreens("Splash")
    object Login : MedSyncScreens("Login")
    object Signup : MedSyncScreens("Signup")
    object Home : MedSyncScreens("Home")
    object Report: MedSyncScreens("Report")
    object AddMedication : MedSyncScreens("Add")
    object MedicationConfirmScreen : MedSyncScreens("Confirmation")
    object UpdateMedication : MedSyncScreens("Update")
    object History : MedSyncScreens("History")
    object Settings : MedSyncScreens("Settings")
    object ProfileScreen : MedSyncScreens(Constants.PROFILE_SCREEN)
    object PrescriptionScreen : MedSyncScreens(Constants.PRESCRIPTION_SCREEN)
}
