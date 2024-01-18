package com.aritra.medsync.domain.repository

import com.aritra.medsync.data.dao.MedicationDao
import com.aritra.medsync.domain.model.Medication
import kotlinx.coroutines.flow.Flow

class MedicationRepositoryImpl(
    private val dao: MedicationDao
) : MedicationRepository {
    override suspend fun insertMedications(medication: List<Medication>) {
        medication.forEach { dao.insertMedication(it) }
    }

    override suspend fun updateMedication(medication: Medication) {
        dao.updateMedication(medication)
    }

    override fun getAllMedications(): Flow<List<Medication>> {
        return dao.getAllMedications()
    }
}