package com.aritra.medsync.services

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.utils.Constants
import java.util.Calendar

class MedSyncNotificationService(
    private val context: Context
) {

    @SuppressLint("ScheduleExactAlarm")
    fun scheduleNotification(medication: Medication) {
        // Get start and end dates
        val startCalendar = Calendar.getInstance()
        startCalendar.time = medication.reminderTime

        val endCalendar = Calendar.getInstance()
        endCalendar.time = medication.endDate

        // Set end date to end of day
        endCalendar.set(Calendar.HOUR_OF_DAY, 23)
        endCalendar.set(Calendar.MINUTE, 59)
        endCalendar.set(Calendar.SECOND, 59)

        // Schedule repeating notification for every day from start to end date
        val alarmService = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Extract hour and minute from reminder time
        val hour = startCalendar.get(Calendar.HOUR_OF_DAY)
        val minute = startCalendar.get(Calendar.MINUTE)

        // Create a unique request code for this medication
        val baseRequestCode = medication.id * 1000
        var requestCode = baseRequestCode

        // Schedule a notification for each day in the range
        val currentCalendar = Calendar.getInstance()
        currentCalendar.time = medication.startDate
        currentCalendar.set(Calendar.HOUR_OF_DAY, hour)
        currentCalendar.set(Calendar.MINUTE, minute)
        currentCalendar.set(Calendar.SECOND, 0)
        currentCalendar.set(Calendar.MILLISECOND, 0)

        while (currentCalendar.timeInMillis <= endCalendar.timeInMillis) {
            val intent = Intent(context, MedSyncNotificationReceiver::class.java)
            intent.putExtra(Constants.MEDICATION_INTENT, medication)

            // Use a unique request code for each day's notification
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode++,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )

            // Schedule the notification
            alarmService.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                currentCalendar.timeInMillis,
                pendingIntent
            )

            // Move to the next day
            currentCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }
    }

    companion object {
        const val MEDICATION_CHANNEL_ID = "medication_channel"
    }
}