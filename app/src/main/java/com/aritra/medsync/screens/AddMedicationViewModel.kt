package com.aritra.medsync.screens

import androidx.lifecycle.ViewModel
import com.aritra.medsync.domain.model.Medication

class AddMedicationViewModel : ViewModel() {

    fun createMedication(
        medicineName: String,
        pillsAmount: Int,
        pillsFrequency: String
    ): List<Medication> {

        val medicationsList = mutableListOf<Medication>()
        val medication = Medication(
            id = 0,
            medicineName = medicineName,
            pillsAmount = pillsAmount,
            pillsFrequency = pillsFrequency
        )
        medicationsList.add(medication)

        return medicationsList
    }
}