package com.aritra.medsync.services

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationManager
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
            NotificationLogger.debug("Scheduling reminder for: ${medication.medicineName} at $hour:$minute")

            // Debug time calculation
            val actualTime = if (hour == 22) "10:00 PM" else "$hour:$minute"
            NotificationLogger.debug("Actual time to be set (hour=$hour, minute=$minute): $actualTime")

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
                val intent = Intent().apply {
                    // Explicitly set the target component
                    setClassName(
                        context.packageName,
                        "com.aritra.medsync.services.MedSyncNotificationReceiver"
                    )
                    putExtra(Constants.MEDICATION_INTENT, medication)
                    // Add action to make intent unique
                    action = "com.aritra.medsync.MEDICATION_REMINDER_${medication.id}_${requestCode}"
                }

                // Create a pending intent with FLAG_IMMUTABLE and FLAG_UPDATE_CURRENT
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    requestCode++,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )

                // Log exact time in milliseconds
                val timeInMillis = currentCalendar.timeInMillis
                val currentTimeMillis = System.currentTimeMillis()
                val differenceMinutes = (timeInMillis - currentTimeMillis) / (1000 * 60)
                
                NotificationLogger.debug("Alarm time details: ${medication.medicineName}")
                NotificationLogger.debug("Current time millis: $currentTimeMillis")
                NotificationLogger.debug("Alarm set for millis: $timeInMillis")
                NotificationLogger.debug("Time until alarm: $differenceMinutes minutes")

                // Schedule the alarm
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        if (alarmManager.canScheduleExactAlarms()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                // Use high-priority alarm for Android 13+
                                setAlarmExactAndAllowWhileIdle(alarmManager, timeInMillis, pendingIntent)
                                NotificationLogger.debug("Set high-priority exact alarm for medication: ${medication.medicineName}")
                            } else {
                                setAlarmExactAndAllowWhileIdle(alarmManager, timeInMillis, pendingIntent)
                            }
                        } else {
                            // Fallback to inexact alarm
                            alarmManager.setAndAllowWhileIdle(
                                AlarmManager.RTC_WAKEUP,
                                timeInMillis,
                                pendingIntent
                            )
                        }
                    } else {
                        setAlarmExactAndAllowWhileIdle(alarmManager, timeInMillis, pendingIntent)
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

    fun cancelScheduledNotification(medicationId: Int) {
        try {
            NotificationLogger.debug("Canceling scheduled notifications for medication ID: $medicationId")
            
            // Get the alarm manager service
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            
            // Cancel any existing alarms for this medication
            val baseRequestCode = medicationId * 1000
            for (i in 0..30) { // Cancel potential alarms for up to 30 days
                val requestCode = baseRequestCode + i
                val intent = Intent().apply {
                    setClassName(
                        context.packageName,
                        "com.aritra.medsync.services.MedSyncNotificationReceiver"
                    )
                    // Use the same action pattern as in scheduling
                    action = "com.aritra.medsync.MEDICATION_REMINDER_${medicationId}_${requestCode}"
                }
                
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    requestCode,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_NO_CREATE
                )
                
                pendingIntent?.let {
                    alarmManager.cancel(it)
                    it.cancel()
                    NotificationLogger.debug("Canceled alarm for medication ID: $medicationId, request code: $requestCode")
                }
            }
            
            // Also cancel any active notification
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(medicationId)
            
            NotificationLogger.debug("All notifications canceled for medication ID: $medicationId")
        } catch (e: Exception) {
            NotificationLogger.error("Error canceling notifications", e)
        }
    }

    fun testNotification(medication: Medication) {
        try {
            NotificationLogger.debug("Testing notification for medication: ${medication.medicineName}")
            
            // Create a test intent with the medication data
            val intent = Intent().apply {
                setClassName(
                    context.packageName,
                    "com.aritra.medsync.services.MedSyncNotificationReceiver"
                )
                putExtra(Constants.MEDICATION_INTENT, medication)
                action = "com.aritra.medsync.MEDICATION_TEST"
            }
            
            // Send the broadcast immediately to test notification display
            context.sendBroadcast(intent)
            NotificationLogger.debug("Test notification broadcast sent")
            
            // Also schedule a notification for 10 seconds from now
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                medication.id * 1000 + 9999,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
            
            val triggerTime = System.currentTimeMillis() + (10 * 1000) // 10 seconds from now
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        triggerTime,
                        pendingIntent
                    )
                } else {
                    alarmManager.setAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        triggerTime,
                        pendingIntent
                    )
                }
            } else {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerTime,
                    pendingIntent
                )
            }
            
            NotificationLogger.debug("Test alarm scheduled for ${triggerTime}")
        } catch (e: Exception) {
            NotificationLogger.error("Error testing notification", e)
        }
    }

    private fun isAlarmPermissionGranted(alarmManager: AlarmManager): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true // Permission not needed before Android 12
        }
    }

    private fun setAlarmExactAndAllowWhileIdle(alarmManager: AlarmManager, triggerAtMillis: Long, operation: PendingIntent) {
        // Make sure we're calling setExactAndAllowWhileIdle for precise timing
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            operation
        )
        
        // On some devices/Android versions, we may need to set a second alarm as a backup
        val backupTriggerTime = triggerAtMillis - (1000 * 60) // 1 minute earlier as backup
        if (backupTriggerTime > System.currentTimeMillis()) {
            val backupIntent = PendingIntent.getBroadcast(
                context,
                operation.creatorUid + 100000, // ensure a different request code
                Intent(context, MedSyncNotificationReceiver::class.java).apply {
                    action = "com.aritra.medsync.MEDICATION_REMINDER_BACKUP"
                },
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
            
            try {
                alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    backupTriggerTime,
                    backupIntent
                )
                NotificationLogger.debug("Set backup alarm for ${backupTriggerTime}")
            } catch (e: Exception) {
                NotificationLogger.error("Failed to set backup alarm", e)
            }
        }
    }
}