package com.aritra.medsync.navigation

import com.aritra.medsync.utils.Constants

sealed class MedSyncScreens(val name: String) {

    object Splash : MedSyncScreens(Constants.SPLASH_SCREEN)
    object GetStarted: MedSyncScreens(Constants.GET_STARTED_SCREEN)
    object Home : MedSyncScreens(Constants.HOME_SCREEN)
    object AddMedication : MedSyncScreens(Constants.ADD_MEDICATION_SCREEN)
    object MedicationConfirmScreen : MedSyncScreens(Constants.MEDICATION_CONFIRMATION_SCREEN)
    object UpdateMedication : MedSyncScreens(Constants.UPDATE_MEDICATION_SCREEN)
    object History : MedSyncScreens(Constants.HISTORY_SCREEN)
    object Settings : MedSyncScreens(Constants.SETTINGS_SCREEN)
    object ProfileScreen : MedSyncScreens(Constants.PROFILE_SCREEN)
    object PrescriptionScreen : MedSyncScreens(Constants.PRESCRIPTION_SCREEN)
    object AppointmentScreen : MedSyncScreens(Constants.APPOINTMENT_SCREEN)
    object AddAppointmentScreen : MedSyncScreens(Constants.ADD_APPOINTMENT_SCREEN)
    object AppointmentDetailsScreen: MedSyncScreens(Constants.APPOINTMENT_DETAILS_SCREEN)
}
