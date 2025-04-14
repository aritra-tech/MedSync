package com.aritra.medsync.data.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aritra.medsync.data.dao.MedicationDao
import com.aritra.medsync.domain.model.Medication


@Database(
    entities = [Medication::class],
    version = 4,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 3, to = 4)
    ]
)
@TypeConverters(Converters::class)
abstract class MedicationDatabase : RoomDatabase() {
    abstract val medicationDao: MedicationDao
}