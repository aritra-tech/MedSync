package com.aritra.medsync.ui.screens.addMedication.viewModel

import androidx.lifecycle.ViewModel
import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.utils.CalendarInformation
import java.util.Calendar
import java.util.Date

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

        for (time in reminderTime) {
            val calendar = Calendar.getInstance()
            calendar.time = startDate

            val medication = Medication(
                id = 0,
                medicineName = medicineName,
                pillsAmount = pillsAmount,
                endDate = endDate,
                reminderTime = getMedicationTime(time, calendar),
                medicineType = medicineType,
                isTaken = false,
                startDate = startDate
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