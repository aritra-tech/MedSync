package com.aritra.medsync.screens.medicationConfirmation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aritra.medsync.domain.model.MedicationConfirmation
import com.aritra.medsync.domain.repository.MedicationRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MedicationConfirmViewModel @Inject constructor(
    private val repository: MedicationRepository
) : ViewModel() {

    private val _medicationSaved = MutableSharedFlow<Unit>()
    val medicationSaved = _medicationSaved.asSharedFlow()

    fun saveMedication(state: MedicationConfirmation) {
        // TODO: Need to change to runIO
        viewModelScope.launch {
            val medications = state.medication
            val saveMedication = repository.insertMedications(medications)

            _medicationSaved.emit(saveMedication)
        }
    }
}