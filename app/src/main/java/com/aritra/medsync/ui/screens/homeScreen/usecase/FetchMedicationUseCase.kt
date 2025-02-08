package com.aritra.medsync.ui.screens.homeScreen.usecase

import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.domain.repository.MedicationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchMedicationUseCase @Inject constructor(
    private val repository: MedicationRepository
) {

    fun getAllMedications(): Flow<List<Medication>> {
        return repository.getAllMedications()
    }
}