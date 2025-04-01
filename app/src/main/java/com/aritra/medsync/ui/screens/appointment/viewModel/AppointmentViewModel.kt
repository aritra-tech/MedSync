package com.aritra.medsync.ui.screens.appointment.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aritra.medsync.domain.extensions.runIO
import com.aritra.medsync.domain.model.Appointment
import com.aritra.medsync.ui.screens.appointment.state.AppointmentUiState
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class AppointmentViewModel : ViewModel() {

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
        doctorSpecialization: String,
        appointmentDate: Date?,
        appointmentTime: Date?
    ) = runIO {
        if (doctorName.isBlank() || doctorSpecialization.isBlank()) {
            _uiState.postValue(AppointmentUiState.Error("Doctor name and specialization are required"))
            return@runIO
        }

        if (appointmentDate == null) {
            _uiState.postValue(AppointmentUiState.Error("Please select appointment date"))
            return@runIO
        }

        if (appointmentTime == null) {
            _uiState.postValue(AppointmentUiState.Error("Please select appointment time"))
            return@runIO
        }

        _uiState.postValue(AppointmentUiState.Loading)
        val istTimeZone = TimeZone.getTimeZone("Asia/Kolkata")
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
            timeZone = istTimeZone
        }
        val formattedDate = if (true) dateFormat.format(appointmentDate) else ""
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault()).apply {
            timeZone = istTimeZone
        }
        val formattedTime = if (true) timeFormat.format(appointmentTime) else ""

        val appointment = hashMapOf(
            "name" to doctorName,
            "specialization" to doctorSpecialization,
            "appointmentDate" to formattedDate,
            "appointmentTime" to formattedTime
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
                    val dateString = document.getString("appointmentDate") ?: ""
                    val timeString = document.getString("appointmentTime") ?: ""

                    Appointment(
                        id = document.id,
                        doctorName = document.getString("name") ?: "",
                        doctorSpecialization = document.getString("specialization") ?: "",
                        appointmentDate = dateString,
                        appointmentTime = timeString
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