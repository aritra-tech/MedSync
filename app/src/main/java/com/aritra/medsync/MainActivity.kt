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
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.aritra.medsync.navigation.MedSyncApp
import com.aritra.medsync.ui.screens.intro.GoogleAuthUiClient
import com.aritra.medsync.ui.theme.MedSyncTheme
import com.aritra.medsync.utils.NotificationLogger
import com.aritra.medsync.utils.PermissionHelper
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.BuildConfig
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity(), InstallStateUpdatedListener {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            content = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }
    private var updateState by mutableStateOf(UpdateState())

    private data class UpdateState(
        val showSnackbar: Boolean = false,
        val message: String = ""
    )

    private lateinit var appUpdateManager: AppUpdateManager
    private var showSnackbar by mutableStateOf(false)

    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result: ActivityResult ->
        if (result.resultCode != RESULT_OK) {
            Timber.d("Update flow failed! Result code: ${result.resultCode}")
            if (BuildConfig.DEBUG) {
                Toast.makeText(
                    applicationContext,
                    "Update flow failed! Result code: ${result.resultCode}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Timber.d("Update flow succeeded")
        }
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

        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        appUpdateManager.registerListener(this)

        PermissionHelper.checkAndRequestNotificationPermission(this) { granted ->
            if (granted) {
                NotificationLogger.debug("Notification permission granted")
            } else {
                NotificationLogger.debug("Notification permission denied")
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                Toast.makeText(
                    this,
                    "Please enable exact alarms for reliable medication reminders",
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                startActivity(intent)
            }
        }

        super.onCreate(savedInstanceState)

        checkForAppUpdate()

        setContent {
            MedSyncTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()

                if (updateState.showSnackbar) {
                    LaunchedEffect(updateState) {
                        snackbarHostState.showSnackbar(
                            message = updateState.message,
                            actionLabel = "INSTALL",
                            duration = SnackbarDuration.Indefinite
                        )

                        updateState = updateState.copy(showSnackbar = false)
                    }
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    MedSyncApp(googleAuthUiClient)

                    SnackbarHost(
                        hostState = snackbarHostState,
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
            }
        }
    }

    private fun checkForAppUpdate() {
        Timber.d("Checking for app updates...")
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            Timber.d("Update availability: ${appUpdateInfo.updateAvailability()}")

            when (appUpdateInfo.updateAvailability()) {
                UpdateAvailability.UPDATE_AVAILABLE -> {
                    Timber.d("Update available! Version code: ${appUpdateInfo.availableVersionCode()}")

                    val updatePriority = try {
                        appUpdateInfo.updatePriority()
                    } catch (e: Exception) {
                        0
                    }

                    if (updatePriority >= 4 && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                        Timber.d("Starting IMMEDIATE update flow (high priority)")
                        startImmediateUpdate(appUpdateInfo)
                    }
                    else if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                        Timber.d("Starting FLEXIBLE update flow")
                        startFlexibleUpdate(appUpdateInfo)
                    } else if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                        Timber.d("Starting IMMEDIATE update flow")
                        startImmediateUpdate(appUpdateInfo)
                    } else {
                        Timber.d("No supported update types allowed")
                    }
                }

                UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS -> {
                    Timber.d("Update already in progress")
                    if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                        startImmediateUpdate(appUpdateInfo)
                    }
                }

                else -> {
                    Timber.d("No update available or unknown state: ${appUpdateInfo.updateAvailability()}")
                }
            }
        }.addOnFailureListener { exception ->
            Timber.e(exception, "Failed to check for updates")
        }
    }

    private fun startImmediateUpdate(appUpdateInfo: AppUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                appUpdateInfo,
                activityResultLauncher,
                AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()
            )
        } catch (e: Exception) {
            Timber.e(e, "Error starting immediate update flow")
        }
    }

    private fun startFlexibleUpdate(appUpdateInfo: AppUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                appUpdateInfo,
                activityResultLauncher,
                AppUpdateOptions.newBuilder(AppUpdateType.FLEXIBLE).build()
            )
        } catch (e: Exception) {
            Timber.e(e, "Error starting flexible update flow")
        }
    }

    override fun onStateUpdate(state: InstallState) {
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            updateState = updateState.copy(
                showSnackbar = true,
                message = "An update has been downloaded."
            )
        }
        Timber.d("Install state updated: ${state.installStatus()}")
    }

    override fun onResume() {
        super.onResume()

        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                Timber.d("Update downloaded but not installed")
                showSnackbar = true
            }

            if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                Timber.d("Update in progress - resuming")
                try {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        activityResultLauncher,
                        AppUpdateOptions.newBuilder(appUpdateInfo.updatePriority()).build()
                    )
                } catch (e: Exception) {
                    Timber.e(e, "Error resuming update")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        appUpdateManager.unregisterListener(this)
    }
}