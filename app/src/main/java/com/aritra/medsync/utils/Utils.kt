package com.aritra.medsync.utils

import com.aritra.medsync.domain.model.Medication

object Utils {

    fun Medication.getMedicationItem() : List<Pair<String, String>> {

        val medicationItems = mutableListOf<Pair<String, String>>()

        medicationItems.add(Pair("Medication Name", medicineName))
        medicationItems.add(Pair("Medication Dose", pillsAmount))
        medicationItems.add(Pair("Medication Frequency", pillsFrequency))

        return medicationItems
    }

    fun getMedicineUnit(medicineType: String): String {
        return when(medicineType) {
            "TABLET" -> "Tablet"
            "CAPSULE" -> "Capsule"
            "SYRUP" -> "mL"
            "INHALER" -> "Puffs"
            else -> "None"
        }
    }
}