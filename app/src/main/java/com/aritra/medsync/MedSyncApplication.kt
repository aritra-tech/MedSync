package com.aritra.medsync

import android.app.Application
import com.aritra.medsync.services.MedicationNotificationHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MedSyncApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MedicationNotificationHelper.createNotificationChannel(this)
    }
}