package com.aritra.medsync.ui.screens.appointment.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aritra.medsync.domain.extensions.runIO
import com.aritra.medsync.domain.model.Appointment
import com.google.firebase.firestore.FirebaseFirestore

class AppointmentViewModel: ViewModel() {
    val userDB = FirebaseFirestore.getInstance()
    private val errorLiveData = MutableLiveData<String>()
    private val saveAppointmentStatus = MutableLiveData<Boolean>()

    private val _appointments = MutableLiveData<List<Appointment>>()
    val appointments: LiveData<List<Appointment>> = _appointments

    init {
        fetchAppointments()
    }

    fun saveAppointments(
        doctorName: String,
        doctorSpecialization: String
    ) = runIO {
        val appointment = hashMapOf(
            "name" to doctorName,
            "specialization" to doctorSpecialization
        )

        userDB.collection("Appointments")
            .add(appointment)
            .addOnSuccessListener {
                saveAppointmentStatus.postValue(true)
                fetchAppointments()
            }
            .addOnFailureListener { e ->
                errorLiveData.postValue("Failed to create user profile: ${e.message}")
            }
    }

    fun fetchAppointments() = runIO {
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
            }
            .addOnFailureListener { e ->
                errorLiveData.postValue("Failed to fetch appointments: ${e.message}")
            }
    }
}