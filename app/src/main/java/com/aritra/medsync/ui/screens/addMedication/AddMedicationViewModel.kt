package com.aritra.medsync.ui.screens.addMedication

import androidx.lifecycle.ViewModel
import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.utils.CalendarInformation
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import java.util.Date

@HiltViewModel
class AddMedicationViewModel : ViewModel() {

    fun createMedication(
        medicineName: String,
        pillsAmount: String,
        endDate: Date,
        reminderTime: List<CalendarInformation>,
        medicineType: String,
        startDate: Date = Date(),
    ): List<Medication> {


        val medicationsList = mutableListOf<Medication>()
        val calendar = Calendar.getInstance()
        calendar.time = startDate
        for (time in reminderTime) {
            val medication = Medication(
                id = 0,
                medicineName = medicineName,
                pillsAmount = pillsAmount,
                endDate = endDate,
                reminderTime = getMedicationTime(time, calendar),
                medicineType = medicineType,
                isTaken = false
            )
            medicationsList.add(medication)
        }

        return medicationsList
    }

    private fun getMedicationTime(time: CalendarInformation, calendar: Calendar): Date {
        calendar.set(Calendar.HOUR_OF_DAY, time.dateInformation.hour)
        calendar.set(Calendar.MINUTE, time.dateInformation.minute)
        return calendar.time
    }
}