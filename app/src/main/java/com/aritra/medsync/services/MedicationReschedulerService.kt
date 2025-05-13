package com.aritra.medsync.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.aritra.medsync.domain.repository.MedicationRepository
import com.aritra.medsync.utils.NotificationLogger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MedicationReschedulerService : Service() {

    @Inject
    lateinit var medicationRepository: MedicationRepository

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        NotificationLogger.debug("MedicationReschedulerService created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        NotificationLogger.debug("MedicationReschedulerService started")
        
        serviceScope.launch {
            try {
                val medSyncNotificationService = MedSyncNotificationService(applicationContext)
                val medications = medicationRepository.getAllMedications().first()
                
                NotificationLogger.debug("Found ${medications.size} medications to reschedule")
                
                for (medication in medications) {
                    // Only schedule if not taken and not expired
                    if (!medication.isTaken && medication.endDate.time >= System.currentTimeMillis()) {
                        medSyncNotificationService.scheduleNotification(medication)
                        NotificationLogger.debug("Rescheduled medication: ${medication.medicineName}")
                    }
                }
                
                NotificationLogger.debug("All medications rescheduled")
            } catch (e: Exception) {
                NotificationLogger.error("Error rescheduling medications", e)
            } finally {
                stopSelf()
            }
        }
        
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        serviceScope.cancel()
        super.onDestroy()
    }
}