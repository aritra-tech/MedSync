package com.aritra.medsync.data.db

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import com.aritra.medsync.data.dao.MedicationDao
import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.utils.Converters


@Database(
    entities = [Medication::class],
    version = 2,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class MedicationDatabase : RoomDatabase() {
    abstract val medicationDao: MedicationDao
}