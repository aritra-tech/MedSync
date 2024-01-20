package com.aritra.medsync.screens.homeScreen.viewmodel

import androidx.lifecycle.ViewModel
import com.aritra.medsync.domain.extensions.runIO
import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.screens.homeScreen.usecase.MedicationUpdateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MedicationDetailsViewModel @Inject constructor(
    private val medicationUpdateUseCase: MedicationUpdateUseCase
) : ViewModel() {

    fun isMedicationTaken(medication: Medication, isMedicationTaken: Boolean) = runIO {
        medicationUpdateUseCase.updateMedication(medication.copy(isTaken = isMedicationTaken))
    }
}