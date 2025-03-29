package com.aritra.medsync.ui.screens.appointment.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aritra.medsync.domain.extensions.runIO
import com.aritra.medsync.domain.model.Appointment
import com.aritra.medsync.ui.screens.appointment.state.AppointmentUiState
import com.google.firebase.firestore.FirebaseFirestore

class AppointmentViewModel: ViewModel() {

    private val userDB = FirebaseFirestore.getInstance()

    private val _uiState = MutableLiveData<AppointmentUiState>(AppointmentUiState.Idle)
    val uiState: LiveData<AppointmentUiState> = _uiState

    private val _appointments = MutableLiveData<List<Appointment>>()
    val appointments: LiveData<List<Appointment>> = _appointments

    init {
        fetchAppointments()
    }

    fun saveAppointments(
        doctorName: String,
        doctorSpecialization: String
    ) = runIO {

        if (doctorName.isBlank() || doctorSpecialization.isBlank()) {
            _uiState.postValue(AppointmentUiState.Error("Field are required"))
            return@runIO
        }

        _uiState.postValue(AppointmentUiState.Loading)

        val appointment = hashMapOf(
            "name" to doctorName,
            "specialization" to doctorSpecialization
        )

        userDB.collection("Appointments")
            .add(appointment)
            .addOnSuccessListener { documentRef ->
                _uiState.postValue(AppointmentUiState.Success(documentRef.id))
                fetchAppointments()
            }
            .addOnFailureListener { e ->
                _uiState.postValue(AppointmentUiState.Error("Failed to save appointment: ${e.message}"))
            }
    }

    fun fetchAppointments() = runIO {

        _uiState.postValue(AppointmentUiState.Loading)

        userDB.collection("Appointments")
            .get()
            .addOnSuccessListener { result ->
                val appointments = result.documents.map { document ->
                    Appointment(
                        id = document.id,
                        doctorName = document.getString("name") ?: "",
                        doctorSpecialization = document.getString("specialization") ?: "",
                    )
                }
                _appointments.postValue(appointments)
                _uiState.postValue(AppointmentUiState.Idle)
            }
            .addOnFailureListener { e ->
                _uiState.postValue(AppointmentUiState.Error("Failed to fetch appointments: ${e.message}"))
            }
    }
}