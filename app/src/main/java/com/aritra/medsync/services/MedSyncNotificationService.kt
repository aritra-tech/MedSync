package com.aritra.medsync.services

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.utils.Constants

class MedSyncNotificationService(
    private val context: Context
) {

    @SuppressLint("ScheduleExactAlarm")
    fun scheduleNotification(medication: Medication) {
        val intent = Intent(context, MedSyncNotificationReceiver::class.java)
        intent.putExtra(Constants.MEDICATION_INTENT, medication)

        val broadcastIntent = PendingIntent.getBroadcast(
            context,
            medication.id,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val alarmService = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = medication.reminderTime.time

        alarmService.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            broadcastIntent
        )
    }

    companion object {
        const val MEDICATION_CHANNEL_ID = "medication_channel"
    }
}