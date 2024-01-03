package com.aritra.medsync.data.db

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.aritra.medsync.data.dao.MedicationDao
import com.aritra.medsync.domain.model.Medication


@Database(
    entities = [Medication::class],
    version = 1,
    exportSchema = true
)

abstract class MedicationDatabase : RoomDatabase() {
    abstract val medicationDao: MedicationDao
}