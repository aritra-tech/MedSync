package com.aritra.medsync

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.aritra.medsync.services.MedSyncNotificationService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MedSyncApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotification()
    }

    private fun createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                MedSyncNotificationService.MEDICATION_CHANNEL_ID,
                getString(R.string.medicine_reminder_title),
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = getString(R.string.notification_for_medication_reminder)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}