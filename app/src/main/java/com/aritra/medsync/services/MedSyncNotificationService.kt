package com.aritra.medsync.services

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.utils.Constants
import com.aritra.medsync.utils.NotificationLogger
import java.util.Calendar

class MedSyncNotificationService(
    private val context: Context
) {
    companion object {
        const val MEDICATION_CHANNEL_ID = "medication_channel"
    }

    @SuppressLint("ScheduleExactAlarm")
    fun scheduleNotification(medication: Medication) {
        try {
            NotificationLogger.debug("Scheduling notification for medication: ${medication.medicineName}")

            // Check if notification permissions are granted
            if (!MedicationNotificationHelper.areNotificationsEnabled(context)) {
                NotificationLogger.error("Notifications are not enabled for the app")
                return
            }

            // Get the alarm manager service
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            if (!isAlarmPermissionGranted(alarmManager)) {
                NotificationLogger.error("Exact alarm permission not granted")
                return
            }

            // Get reminder time details
            val reminderCalendar = Calendar.getInstance()
            reminderCalendar.time = medication.reminderTime

            val hour = reminderCalendar.get(Calendar.HOUR_OF_DAY)
            val minute = reminderCalendar.get(Calendar.MINUTE)

            NotificationLogger.debug("Reminder time: $hour:$minute")

            // Get end date
            val endCalendar = Calendar.getInstance()
            endCalendar.time = medication.endDate
            endCalendar.set(Calendar.HOUR_OF_DAY, 23)
            endCalendar.set(Calendar.MINUTE, 59)
            endCalendar.set(Calendar.SECOND, 59)

            // Start from today or medication start date, whichever is later
            val currentCalendar = Calendar.getInstance()
            if (medication.startDate.after(currentCalendar.time)) {
                currentCalendar.time = medication.startDate
            }

            // Set the hour and minute from the reminder time
            currentCalendar.set(Calendar.HOUR_OF_DAY, hour)
            currentCalendar.set(Calendar.MINUTE, minute)
            currentCalendar.set(Calendar.SECOND, 0)
            currentCalendar.set(Calendar.MILLISECOND, 0)

            // If time for today has already passed, start from tomorrow
            if (currentCalendar.timeInMillis < System.currentTimeMillis()) {
                currentCalendar.add(Calendar.DAY_OF_MONTH, 1)
                NotificationLogger.debug("Time for today already passed, scheduling for tomorrow")
            }

            // Unique request code for this medication
            val baseRequestCode = medication.id * 1000
            var requestCode = baseRequestCode

            // Schedule notifications for each day until the end date
            while (currentCalendar.timeInMillis <= endCalendar.timeInMillis) {
                val intent = Intent(context, MedSyncNotificationReceiver::class.java).apply {
                    putExtra(Constants.MEDICATION_INTENT, medication)
                    // Add action to make intent unique
                    action = "com.aritra.medsync.MEDICATION_REMINDER_${medication.id}_${requestCode}"
                }

                // Create a pending intent with FLAG_IMMUTABLE
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    requestCode++,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
                )

                // Schedule the alarm
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        if (alarmManager.canScheduleExactAlarms()) {
                            alarmManager.setExactAndAllowWhileIdle(
                                AlarmManager.RTC_WAKEUP,
                                currentCalendar.timeInMillis,
                                pendingIntent
                            )
                        } else {
                            // Fallback to inexact alarm
                            alarmManager.setAndAllowWhileIdle(
                                AlarmManager.RTC_WAKEUP,
                                currentCalendar.timeInMillis,
                                pendingIntent
                            )
                        }
                    } else {
                        alarmManager.setExactAndAllowWhileIdle(
                            AlarmManager.RTC_WAKEUP,
                            currentCalendar.timeInMillis,
                            pendingIntent
                        )
                    }

                    NotificationLogger.debug("Scheduled notification for ${currentCalendar.time}")
                } catch (e: Exception) {
                    NotificationLogger.error("Failed to schedule notification", e)
                }

                // Move to next day
                currentCalendar.add(Calendar.DAY_OF_MONTH, 1)
            }

            NotificationLogger.debug("Finished scheduling notifications for medication: ${medication.medicineName}")
        } catch (e: Exception) {
            NotificationLogger.error("Error in scheduleNotification", e)
        }
    }

    private fun isAlarmPermissionGranted(alarmManager: AlarmManager): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true // Permission not needed before Android 12
        }
    }
}