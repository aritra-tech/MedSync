package com.aritra.medsync.screens.homeScreen.usecase

import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.domain.repository.MedicationRepository
import javax.inject.Inject

class MedicationUpdateUseCase @Inject constructor(
    private val repository: MedicationRepository
) {

    suspend fun updateMedication(medication: Medication) {
        return repository.updateMedication(medication)
    }
}