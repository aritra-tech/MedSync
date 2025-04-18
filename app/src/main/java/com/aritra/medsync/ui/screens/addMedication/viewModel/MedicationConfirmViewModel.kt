package com.aritra.medsync.ui.screens.addMedication.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.aritra.medsync.domain.extensions.runIO
import com.aritra.medsync.domain.model.MedicationConfirmation
import com.aritra.medsync.ui.screens.addMedication.usecase.MedicationConfirmUseCase
import com.aritra.medsync.services.MedSyncNotificationService
import com.aritra.medsync.services.MedicationNotificationHelper
import com.aritra.medsync.utils.NotificationLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class MedicationConfirmViewModel @Inject constructor(
    private val medicationConfirmUseCase: MedicationConfirmUseCase
) : ViewModel() {

    private val _medicationSaved = MutableSharedFlow<Unit>()
    val medicationSaved = _medicationSaved.asSharedFlow()

    fun saveMedication(context: Context, state: MedicationConfirmation) = runIO {
        try {
            NotificationLogger.debug("Saving medication and scheduling notifications")

            // Ensure notification channel is created
            MedicationNotificationHelper.createNotificationChannel(context)

            val medications = state.medication
            val saveMedication = medicationConfirmUseCase.saveMedication(medications)

            // Create notification service
            val service = MedSyncNotificationService(context)

            // Schedule notifications for each medication
            for (medication in medications) {
                NotificationLogger.debug("Scheduling notification for: ${medication.medicineName}")
                service.scheduleNotification(medication)

                // For immediate testing
                // service.scheduleTestNotification(medication)
            }

            NotificationLogger.debug("All medications saved and notifications scheduled")
            _medicationSaved.emit(saveMedication)
        } catch (e: Exception) {
            NotificationLogger.error("Error saving medication", e)
        }
    }
}