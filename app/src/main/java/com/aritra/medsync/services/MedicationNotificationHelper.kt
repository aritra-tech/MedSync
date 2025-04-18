package com.aritra.medsync.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import timber.log.Timber

class MedicationNotificationHelper {
    companion object {
        private const val TAG = "NotificationHelper"

        fun createNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = "Medication Reminders"
                val descriptionText = "Notifications for your medication schedule"
                val importance = NotificationManager.IMPORTANCE_HIGH
                val channel = NotificationChannel(
                    MedSyncNotificationService.MEDICATION_CHANNEL_ID,
                    name,
                    importance
                ).apply {
                    description = descriptionText
                    enableVibration(true)
                    enableLights(true)
                    setShowBadge(true)
                }

                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
                Timber.tag(TAG).d("Notification channel created")
            }
        }

        fun areNotificationsEnabled(context: Context): Boolean {
            // Check if notifications are enabled globally
            val notificationEnabled = NotificationManagerCompat.from(context).areNotificationsEnabled()

            // For Android 13+, check for the POST_NOTIFICATIONS permission
            val postNotificationPermissionGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            } else {
                true // Permission not needed below Android 13
            }

            Timber.tag(TAG)
                .d("Notifications enabled: $notificationEnabled, POST_NOTIFICATIONS granted: $postNotificationPermissionGranted")
            return notificationEnabled && postNotificationPermissionGranted
        }
    }
}