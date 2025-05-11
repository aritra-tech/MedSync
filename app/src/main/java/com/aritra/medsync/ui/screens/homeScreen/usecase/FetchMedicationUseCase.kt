package com.aritra.medsync.ui.screens.homeScreen.usecase

import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.domain.repository.MedicationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject

class FetchMedicationUseCase @Inject constructor(
    private val repository: MedicationRepository
) {

    fun getAllMedications(): Flow<List<Medication>> {
        return repository.getAllMedications().map { medications ->
            val currentTime = Date().time
            val twentyFourHoursInMillis = 24 * 60 * 60 * 1000L
            
            medications.filter { medication ->
                val medicationAge = currentTime - medication.startDate.time
                medicationAge <= twentyFourHoursInMillis
            }
        }
    }
}