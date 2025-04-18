package com.aritra.medsync.services

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.aritra.medsync.R
import com.aritra.medsync.MainActivity
import java.util.Calendar

class AppointmentAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val doctorName = intent.getStringExtra("doctorName") ?: ""
        val appointmentTime = intent.getStringExtra("appointmentTime") ?: ""
        val appointmentId = intent.getStringExtra("appointmentId") ?: ""

        // Create and show the notification
        val notificationHelper = NotificationHelper(context)
        notificationHelper.createNotification(
            title = "Dr. $doctorName • $appointmentTime",
            message = "Hey you have an appointment with Dr. $doctorName",
            appointmentId = appointmentId
        )
    }
}

class NotificationHelper(private val context: Context) {
    companion object {
        private const val CHANNEL_ID = "appointment_channel"
        private const val CHANNEL_NAME = "Appointment Notifications"
        private const val CHANNEL_DESCRIPTION = "Notifications for upcoming doctor appointments"
    }

    private val notificationManager: NotificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = CHANNEL_DESCRIPTION
                enableLights(true)
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun createNotification(title: String, message: String, appointmentId: String) {
        val intent = Intent().apply {
            setClassName(context.packageName, MainActivity::class.java.name)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            appointmentId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.medsync_logo)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(appointmentId.hashCode(), notification)
    }
}

class AppointmentNotificationScheduler(private val context: Context) {
    private val alarmManager: AlarmManager by lazy {
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    fun scheduleAppointmentReminder(
        appointmentId: String,
        doctorName: String,
        appointmentDate: String, // Format: "yyyy-MM-dd"
        appointmentTime: String, // Format: "hh:mm a"
        reminderMinutes: Int = 30 // Default reminder 30 minutes before appointment
    ) {
        // Parse date and time
        val dateParts = appointmentDate.split("-")
        val year = dateParts[0].toInt()
        val month = dateParts[1].toInt() - 1 // Calendar months are 0-based
        val day = dateParts[2].toInt()

        val timeParts = appointmentTime.split(":")
        val hourPart = timeParts[0]
        val minuteAndAmPm = timeParts[1].split(" ")
        val minute = minuteAndAmPm[0].toInt()
        val amPm = minuteAndAmPm[1]

        var hour = hourPart.toInt()
        // Convert to 24-hour format if needed
        if (amPm.equals("PM", ignoreCase = true) && hour < 12) {
            hour += 12
        } else if (amPm.equals("AM", ignoreCase = true) && hour == 12) {
            hour = 0
        }

        // Set the calendar to the appointment time
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, day)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        // Subtract reminder time
        calendar.add(Calendar.MINUTE, -reminderMinutes)

        // Create intent for the alarm
        val intent = Intent().apply {
            setClassName(context, "com.aritra.medsync.services.AppointmentAlarmReceiver")
            putExtra("doctorName", doctorName)
            putExtra("appointmentTime", appointmentTime)
            putExtra("appointmentId", appointmentId)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            appointmentId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Schedule the alarm
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }
}