package com.aritra.medsync

import android.app.AlarmManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.aritra.medsync.navigation.MedSyncApp
import com.aritra.medsync.ui.screens.intro.GoogleAuthUiClient
import com.aritra.medsync.ui.theme.MedSyncTheme
import com.aritra.medsync.utils.NotificationLogger
import com.aritra.medsync.utils.PermissionHelper
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            content = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.Transparent.toArgb(), Color.Transparent.toArgb()
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.Transparent.toArgb(), Color.Transparent.toArgb()
            )
        )

        PermissionHelper.checkAndRequestNotificationPermission(this) { granted ->
            if (granted) {
                NotificationLogger.debug("Notification permission granted")
            } else {
                NotificationLogger.debug("Notification permission denied")
                // Show a message to the user explaining that they won't receive medication reminders
            }
        }

        // Check for SCHEDULE_EXACT_ALARM permission on Android 12+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                // Inform the user that they need to enable exact alarms
                Toast.makeText(
                    this,
                    "Please enable exact alarms for reliable medication reminders",
                    Toast.LENGTH_LONG
                ).show()

                // Open settings to enable exact alarms
                val intent = Intent(android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                startActivity(intent)
            }
        }

        super.onCreate(savedInstanceState)
        setContent {
            MedSyncTheme {
                MedSyncApp(googleAuthUiClient)
            }
        }
    }
}