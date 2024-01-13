package com.aritra.medsync.screens.medicationConfirmation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aritra.medsync.domain.extensions.runIO
import com.aritra.medsync.domain.model.MedicationConfirmation
import com.aritra.medsync.screens.medicationConfirmation.usecase.MedicationConfirmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicationConfirmViewModel @Inject constructor(
    private val medicationConfirmUseCase: MedicationConfirmUseCase
) : ViewModel() {

    private val _medicationSaved = MutableSharedFlow<Unit>()
    val medicationSaved = _medicationSaved.asSharedFlow()

    fun saveMedication(state: MedicationConfirmation) = runIO {
        val medications = state.medication
        val saveMedication = medicationConfirmUseCase.saveMedication(medications)
        _medicationSaved.emit(saveMedication)
    }
}