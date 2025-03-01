package com.aritra.medsync.domain.repository

import com.aritra.medsync.domain.model.Medication
import kotlinx.coroutines.flow.Flow

interface MedicationRepository {

    suspend fun insertMedications(medication: List<Medication>)

    suspend fun updateMedication(medication: Medication)

    fun getAllMedications(): Flow<List<Medication>>

    suspend fun getMedicationById(medicationId: Int): Medication?
}