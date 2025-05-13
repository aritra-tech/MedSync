package com.aritra.medsync.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aritra.medsync.domain.model.Medication
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface MedicationDao {

    @Query("SELECT * FROM medication WHERE id = :medicationId")
    suspend fun getMedicationById(medicationId: Int) : Medication

    @Query("SELECT * FROM medication")
    fun getAllMedications() : Flow<List<Medication>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedication(medicationModel: Medication): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMedication(medicationModel: Medication)
    
    @Query("DELETE FROM medication WHERE startDate < :cutoffDate")
    suspend fun deleteOldMedications(cutoffDate: Date)
}