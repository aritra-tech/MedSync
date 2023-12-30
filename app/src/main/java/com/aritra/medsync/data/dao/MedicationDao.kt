package com.aritra.medsync.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aritra.medsync.domain.model.Medication

@Dao
interface MedicationDao {

    @Query("SELECT * FROM medication WHERE id = :medicationId")
    suspend fun getMedicationById(medicationId: Int) : Medication

//    @Query("SELECT * FROM medication ORDER BY date DESC")
//    suspend fun getAllMedication() : List<Medication>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(medicationModel: Medication): Long

    @Update
    suspend fun updateMedication(medicationModel: Medication)
}