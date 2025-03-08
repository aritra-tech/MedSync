package com.aritra.medsync.ui.screens.medicationConfirmation.usecase

import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.domain.repository.MedicationRepository
import javax.inject.Inject

class MedicationConfirmUseCase @Inject constructor(
    private val repository: MedicationRepository
) {
    suspend fun saveMedication(medications: List<Medication>) {
        repository.insertMedications(medications)
    }
}