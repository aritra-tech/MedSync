package com.aritra.medsync.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.aritra.medsync.MainActivity
import com.aritra.medsync.R
import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.utils.Constants
import com.aritra.medsync.utils.toFormattedTimeString
import com.aritra.medsync.workers.MedicationUpdateWorker

class MedSyncNotificationReceiver : BroadcastReceiver() {

    companion object {
        const val ACTION_TAKE = "com.aritra.medsync.ACTION_TAKE"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            when (intent?.action) {
                ACTION_TAKE -> {
                    // Handle "Take" action
                    val medicationId = intent.getIntExtra(Constants.MEDICATION_ID, -1)
                    if (medicationId != -1) {
                        handleMedicationTaken(context, medicationId)
                    } else {

                    }
                }
                else -> {
                    // Show notification
                    intent?.getParcelableExtra<Medication>(Constants.MEDICATION_INTENT)?.let { medication ->
                        showNotification(it, medication)
                    }
                }
            }
        }
    }

    private fun handleMedicationTaken(context: Context, medicationId: Int) {
        val workRequest = OneTimeWorkRequestBuilder<MedicationUpdateWorker>()
            .setInputData(
                workDataOf(
                    Constants.MEDICATION_ID to medicationId,
                    Constants.MEDICATION_TAKEN to true
                )
            )
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)

        // Cancel the notification
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(medicationId)
    }

    private fun showNotification(context: Context, medication: Medication) {
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra(Constants.MEDICATION_NOTIFICATION, true)
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

        val notification = NotificationCompat.Builder(
            context,
            MedSyncNotificationService.MEDICATION_CHANNEL_ID
        )
            .setSmallIcon(R.drawable.medsync_logo)
            .setContentTitle(context.getString(R.string.medicine_reminder_title, medication.reminderTime.toFormattedTimeString()))
            .setContentText(context.getString(R.string.medicine_reminder_text, medication.medicineName))
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.take_icon, "Take", takePendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(medication.id, notification)
    }
}