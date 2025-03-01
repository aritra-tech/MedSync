package com.aritra.medsync.ui.screens.homeScreen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.aritra.medsync.domain.extensions.runIO
import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.domain.repository.MedicationRepository
import com.aritra.medsync.ui.screens.homeScreen.usecase.FetchMedicationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.security.PrivateKey
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchMedicationUseCase: FetchMedicationUseCase,
    private val medicineRepository: MedicationRepository
) : ViewModel() {

    var medicationModel by mutableStateOf(emptyList<Medication>())

    fun getMedications() = runIO {
        fetchMedicationUseCase.getAllMedications().collect { response ->
            medicationModel = response
        }
    }

    fun getMedicationById(medicationId: Int, onResult: (Medication?) -> Unit) = runIO {
        val medication = medicineRepository.getMedicationById(medicationId)
        onResult(medication)
    }

    fun updateMedicationStatus(medication: Medication, isTaken: Boolean, isSkipped: Boolean = false) = runIO {
            val updatedMedication = medication.copy(
                isTaken = isTaken,
                isSkipped = isSkipped
            )
            medicineRepository.updateMedication(updatedMedication)

    }
}