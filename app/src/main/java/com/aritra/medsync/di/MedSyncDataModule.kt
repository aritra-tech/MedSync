package com.aritra.medsync.di

import android.app.Application
import androidx.room.Room
import com.aritra.medsync.data.dao.MedicationDao
import com.aritra.medsync.data.db.MedicationDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MedSyncDataModule {
    @Provides
    @Singleton
    fun provideMedicationDatabase(app: Application): MedicationDatabase {

        return Room.databaseBuilder(
            app,
            MedicationDatabase::class.java,
            "medication_database"
        ).build()
    }

    fun provideMedicationRepository(db: MedicationDatabase): MedicationDao {
        return db.medicationDao()
    }
}