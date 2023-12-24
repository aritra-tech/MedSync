package com.aritra.medsync.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aritra.medsync.data.dao.MedicationDao
import com.aritra.medsync.domain.model.Medication


@Database(entities = [Medication::class], version = 1)

abstract class MedicationDatabase : RoomDatabase() {

    abstract fun medicationDao(): MedicationDao

    companion object {

        private var INSTANCE: MedicationDatabase? = null
        fun getInstance(context: Context): MedicationDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MedicationDatabase::class.java,
                        "MedicationDatabase"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}