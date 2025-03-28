package com.aritra.medsync.ui.screens.appointment.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aritra.medsync.domain.extensions.runIO
import com.google.firebase.firestore.FirebaseFirestore

class AppointmentViewModel: ViewModel() {

    private val errorLiveData = MutableLiveData<String>()
    private val saveAppointmentStatus = MutableLiveData<Boolean>()

    fun saveAppointments(
        doctorName: String,
        doctorSpecialization: String
    ) = runIO {
        val userDB = FirebaseFirestore.getInstance()
        val appointment = hashMapOf(
            "name" to doctorName,
            "specialization" to doctorSpecialization
        )

        userDB.collection("Appointments")
            .add(appointment)
            .addOnSuccessListener {
                saveAppointmentStatus.postValue(true)
            }
            .addOnFailureListener { e ->
                errorLiveData.postValue("Failed to create user profile: ${e.message}")
            }
    }
}