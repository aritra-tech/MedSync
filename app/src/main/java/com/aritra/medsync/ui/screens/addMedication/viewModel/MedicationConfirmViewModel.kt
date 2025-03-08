package com.aritra.medsync.ui.screens.addMedication.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.aritra.medsync.domain.extensions.runIO
import com.aritra.medsync.domain.model.MedicationConfirmation
import com.aritra.medsync.ui.screens.addMedication.usecase.MedicationConfirmUseCase
import com.aritra.medsync.services.MedSyncNotificationService
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

    fun saveMedication(context: Context,state: MedicationConfirmation) = runIO {
        val medications = state.medication
        val saveMedication = medicationConfirmUseCase.saveMedication(medications)

        for (medication in medications) {
            val service = MedSyncNotificationService(context)
            service.scheduleNotification(medication)
        }
        _medicationSaved.emit(saveMedication)
    }
}