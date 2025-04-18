package com.aritra.medsync.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.aritra.medsync.MainActivity
import com.aritra.medsync.R
import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.utils.Constants
import com.aritra.medsync.utils.NotificationLogger
import com.aritra.medsync.utils.toFormattedTimeString

class MedSyncNotificationReceiver : BroadcastReceiver() {

    companion object {
        const val ACTION_TAKE = "com.aritra.medsync.ACTION_TAKE"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        try {
            NotificationLogger.debug("BroadcastReceiver onReceive called with action: ${intent?.action}")

            context?.let {
                when (intent?.action) {
                    ACTION_TAKE -> {
                        // Handle "Take" action
                        val medicationId = intent.getIntExtra(Constants.MEDICATION_ID, -1)
                        if (medicationId != -1) {
                            handleMedicationTaken(context, medicationId)
                        } else {
                            NotificationLogger.error("No medication ID found in the intent")
                        }
                    }
                    "android.intent.action.BOOT_COMPLETED" -> {
                        NotificationLogger.debug("Device rebooted, should reschedule all notifications")
                        // In a real app, you would reschedule all pending medications here
                    }
                    else -> {
                        // Show notification
                        val medication = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            intent?.getParcelableExtra(Constants.MEDICATION_INTENT, Medication::class.java)
                        } else {
                            @Suppress("DEPRECATION")
                            intent?.getParcelableExtra(Constants.MEDICATION_INTENT)
                        }

                        if (medication != null) {
                            NotificationLogger.debug("Showing notification for medication: ${medication.medicineName}")
                            showNotification(it, medication)
                        } else {
                            NotificationLogger.error("No medication found in the intent")
                        }
                    }
                }
            } ?: run {
                NotificationLogger.error("Context is null in onReceive")
            }
        } catch (e: Exception) {
            NotificationLogger.error("Error in onReceive", e)
        }
    }

    private fun handleMedicationTaken(context: Context, medicationId: Int) {
        try {
            NotificationLogger.debug("Handling medication taken for ID: $medicationId")

            // Create an Intent to send a broadcast to update medication in the database
            val updateIntent = Intent("com.aritra.medsync.UPDATE_MEDICATION_STATUS")
            updateIntent.putExtra(Constants.MEDICATION_ID, medicationId)
            updateIntent.putExtra(Constants.MEDICATION_TAKEN, true)
            context.sendBroadcast(updateIntent)

            // Cancel the notification
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(medicationId)

            NotificationLogger.debug("Medication marked as taken and notification canceled")
        } catch (e: Exception) {
            NotificationLogger.error("Error handling medication taken", e)
        }
    }

    private fun showNotification(context: Context, medication: Medication) {
        try {
            NotificationLogger.debug("Building notification for ${medication.medicineName}")

            // Create intent for when user taps notification
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(Constants.MEDICATION_NOTIFICATION, true)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

            val pendingIntent = PendingIntent.getActivity(
                context,
                medication.id,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )

            // Create Take action
            val takeIntent = Intent(context, MedSyncNotificationReceiver::class.java).apply {
                action = ACTION_TAKE
                putExtra(Constants.MEDICATION_ID, medication.id)
            }

            val takePendingIntent = PendingIntent.getBroadcast(
                context,
                medication.id * 10 + 1, // Ensure unique request code
                takeIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

            // Get default notification sound
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            // Build the notification
            val notification = NotificationCompat.Builder(
                context,
                MedSyncNotificationService.MEDICATION_CHANNEL_ID
            )
                .setSmallIcon(R.drawable.medsync_logo)
                .setContentTitle(context.getString(R.string.medicine_reminder_title, medication.reminderTime.toFormattedTimeString()))
                .setContentText(context.getString(R.string.medicine_reminder_text, medication.medicineName))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.take_icon, "Take", takePendingIntent)
                .setSound(defaultSoundUri)
                .setAutoCancel(true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .build()

            // Show the notification
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(medication.id, notification)

            NotificationLogger.debug("Notification displayed successfully")
        } catch (e: Exception) {
            NotificationLogger.error("Error showing notification", e)
        }
    }
}