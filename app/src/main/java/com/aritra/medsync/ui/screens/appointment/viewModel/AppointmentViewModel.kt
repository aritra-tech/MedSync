package com.aritra.medsync.ui.screens.appointment.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.aritra.medsync.domain.extensions.runIO
import com.aritra.medsync.domain.model.Appointment
import com.aritra.medsync.services.AppointmentNotificationScheduler
import com.aritra.medsync.ui.screens.appointment.state.AppointmentUiState
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class AppointmentViewModel(application: Application) : AndroidViewModel(application) {

    private val userDB = FirebaseFirestore.getInstance()
    private val notificationScheduler = AppointmentNotificationScheduler(application.applicationContext)

    private val _uiState = MutableLiveData<AppointmentUiState>(AppointmentUiState.Idle)
    val uiState: LiveData<AppointmentUiState> = _uiState

    private val _appointments = MutableLiveData<Map<String,List<Appointment>>>()
    val appointments: LiveData<Map<String,List<Appointment>>> = _appointments

    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> = _searchQuery

    val filteredAppointments: LiveData<Map<String, List<Appointment>>> = MediatorLiveData<Map<String, List<Appointment>>>().apply {
        addSource(_appointments) { appointments ->
            value = filterAppointments(_searchQuery.value ?: "", appointments)
        }
        addSource(_searchQuery) { query ->
            _appointments.value?.let { appointments ->
                value = filterAppointments(query, appointments)
            }
        }
    }

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
        val formattedDate = dateFormat.format(appointmentDate)

        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault()).apply {
            timeZone = istTimeZone
        }
        val formattedTime = timeFormat.format(appointmentTime)

        val appointment = hashMapOf(
            "name" to doctorName,
            "specialization" to doctorSpecialization,
            "appointmentDate" to formattedDate,
            "appointmentTime" to formattedTime
        )

        userDB.collection("Appointments")
            .add(appointment)
            .addOnSuccessListener { documentRef ->
                // Schedule notification for the appointment
                notificationScheduler.scheduleAppointmentReminder(
                    appointmentId = documentRef.id,
                    doctorName = doctorName,
                    appointmentDate = formattedDate,
                    appointmentTime = formattedTime,
                    reminderMinutes = 60
                )

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
                val appointmentList = result.documents.map { document ->
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
                val groupedAppointmentWithDate = appointmentList.groupBy { it.appointmentDate }
                _appointments.postValue(groupedAppointmentWithDate)
                _uiState.postValue(AppointmentUiState.Idle)
            }
            .addOnFailureListener { e ->
                _uiState.postValue(AppointmentUiState.Error("Failed to fetch appointments: ${e.message}"))
            }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private fun filterAppointments(query: String, appointments: Map<String, List<Appointment>>): Map<String, List<Appointment>> {
        if (query.isBlank()) return appointments

        return appointments.mapValues { entry ->
            entry.value.filter { appointment ->
                appointment.doctorName.contains(query, ignoreCase = true) ||
                        appointment.doctorSpecialization.contains(query, ignoreCase = true)
            }
        }.filterValues { it.isNotEmpty() }
    }

    fun updateAppointment(
        appointmentId: String,
        doctorName: String,
        doctorSpecialization: String,
        appointmentDate: Date,
        appointmentTime: Date
    ) = runIO {
        _uiState.postValue(AppointmentUiState.Loading)

        val istTimeZone = TimeZone.getTimeZone("Asia/Kolkata")
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
            timeZone = istTimeZone
        }
        val formattedDate = dateFormat.format(appointmentDate)

        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault()).apply {
            timeZone = istTimeZone
        }
        val formattedTime = timeFormat.format(appointmentTime)

        val updatedAppointment = hashMapOf(
            "name" to doctorName,
            "specialization" to doctorSpecialization,
            "appointmentDate" to formattedDate,
            "appointmentTime" to formattedTime
        )

        userDB.collection("Appointments")
            .document(appointmentId)
            .update(updatedAppointment as Map<String, Any>)
            .addOnSuccessListener {
                // Reschedule notification with updated time
                notificationScheduler.scheduleAppointmentReminder(
                    appointmentId = appointmentId,
                    doctorName = doctorName,
                    appointmentDate = formattedDate,
                    appointmentTime = formattedTime,
                    reminderMinutes = 5
                )

                _uiState.postValue(AppointmentUiState.Success("Appointment updated"))
                fetchAppointments()
            }
            .addOnFailureListener { e ->
                _uiState.postValue(AppointmentUiState.Error("Failed to update appointment: ${e.message}"))
            }
    }

    fun deleteAppointment(appointmentId: String) = runIO {
        _uiState.postValue(AppointmentUiState.Loading)

        userDB.collection("Appointments")
            .document(appointmentId)
            .delete()
            .addOnSuccessListener {
                _uiState.postValue(AppointmentUiState.Success("Appointment deleted"))
                fetchAppointments()
            }
            .addOnFailureListener { e ->
                _uiState.postValue(AppointmentUiState.Error("Failed to delete appointment: ${e.message}"))
            }
    }
}