package com.aritra.medsync.domain.repository

import com.aritra.medsync.data.dao.MedicationDao
import com.aritra.medsync.domain.model.Medication
import kotlinx.coroutines.flow.Flow
import java.util.Date

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

    override suspend fun getMedicationById(medicationId: Int): Medication? {
        return dao.getMedicationById(medicationId)
    }
    
    override suspend fun deleteOldMedications(timeThreshold: Long) {
        val cutoffDate = Date(System.currentTimeMillis() - timeThreshold)
        dao.deleteOldMedications(cutoffDate)
    }
}