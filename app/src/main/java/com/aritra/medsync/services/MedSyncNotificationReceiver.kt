package com.aritra.medsync.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.aritra.medsync.MainActivity
import com.aritra.medsync.R
import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.utils.Constants
import com.aritra.medsync.utils.toFormattedTimeString

class MedSyncNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            intent?.getParcelableExtra<Medication>(Constants.MEDICATION_INTENT)?.let { medication ->
                showNotification(it, medication)
            }
        }
    }

    private fun showNotification(context: Context, medication: Medication) {

        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra(Constants.MEDICATION_NOTIFICATION, true)
        val pendingIntent = PendingIntent.getActivity(
            context,
            1,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(
            context,
            MedSyncNotificationService.MEDICATION_CHANNEL_ID
        )
            .setSmallIcon(R.drawable.medsync_logo)
            .setContentTitle(context.getString(R.string.medicine_reminder_title, medication.reminderTime.toFormattedTimeString()))
            .setContentText(context.getString(R.string.medicine_reminder_text, medication.medicineName))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()


        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(medication.id, notification)
    }

}