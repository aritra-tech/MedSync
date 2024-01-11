package com.aritra.medsync.data.mapper

import com.aritra.medsync.domain.model.Medication

fun Medication.toMedication(): Medication {
    return Medication(
        id = id,
        medicineName = medicineName,
        pillsAmount = pillsAmount,
        pillsFrequency = pillsFrequency,
        endDate = endDate,
        reminderTime = reminderTime
    )
}