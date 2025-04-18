package com.aritra.medsync.ui.screens.appointment.state

sealed class AppointmentUiState {
    object Idle: AppointmentUiState()
    object Loading: AppointmentUiState()
    data class Success(val appointmentId: String): AppointmentUiState()
    data class Error(val message: String): AppointmentUiState()
}