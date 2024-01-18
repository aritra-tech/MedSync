package com.aritra.medsync.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.text.toLowerCase
import com.aritra.medsync.domain.model.Medication
import java.time.LocalTime
import java.time.ZoneId
import java.util.Locale

object Utils {

    fun Medication.getMedicationItem(): List<Pair<String, String>> {

        val medicationItems = mutableListOf<Pair<String, String>>()

        medicationItems.add(Pair("Medication Name", medicineName))
        medicationItems.add(Pair("Medication Dose", pillsAmount + " " + getMedicineUnit(medicineType)))
        medicationItems.add(Pair("Medication Frequency", pillsFrequency))

        return medicationItems
    }

    fun getMedicineUnit(medicineType: String): String {
        return when (medicineType) {
            "TABLET" -> "Tablet"
            "CAPSULE" -> "Capsule"
            "SYRUP" -> "mL"
            "INHALER" -> "Puffs"
            else -> "None"
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun greetingText(): String {
        return when (LocalTime.now(ZoneId.systemDefault()).hour) {
            in 5..11 -> "Good morning,"
            in 12..16 -> "Good afternoon,"
            in 17..20 -> "Good evening,"
            else -> "Good night,"
        }
    }
}