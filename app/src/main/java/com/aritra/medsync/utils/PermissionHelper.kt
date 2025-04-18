package com.aritra.medsync.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.aritra.medsync.MainActivity

class PermissionHelper {
    companion object {
        fun checkAndRequestNotificationPermission(activity: MainActivity, onPermissionResult: (Boolean) -> Unit) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                when {
                    ContextCompat.checkSelfPermission(
                        activity,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED -> {
                        // Permission already granted
                        NotificationLogger.debug("Notification permission already granted")
                        onPermissionResult(true)
                    }
                    activity.shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                        // Show permission rationale to explain why we need this permission
                        NotificationLogger.debug("Should show notification permission rationale")
                        // Here you would typically show a dialog explaining the permission
                        // For simplicity, we'll just request the permission directly
                        requestNotificationPermission(activity, onPermissionResult)
                    }
                    else -> {
                        // Request the permission directly
                        NotificationLogger.debug("Requesting notification permission")
                        requestNotificationPermission(activity, onPermissionResult)
                    }
                }
            } else {
                // Permission not needed on older Android versions
                NotificationLogger.debug("Notification permission not needed for this Android version")
                onPermissionResult(true)
            }
        }

        private fun requestNotificationPermission(activity: MainActivity, onPermissionResult: (Boolean) -> Unit) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val requestPermissionLauncher = activity.registerForActivityResult(
                    ActivityResultContracts.RequestPermission()
                ) { isGranted: Boolean ->
                    NotificationLogger.debug("Notification permission granted: $isGranted")
                    onPermissionResult(isGranted)
                }

                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                onPermissionResult(true)
            }
        }

        fun checkAlarmPermission(context: Context): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as android.app.AlarmManager
                alarmManager.canScheduleExactAlarms()
            } else {
                true // Permission not needed before Android 12
            }
        }
    }
}