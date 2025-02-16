package com.aritra.medsync.ui.screens.homeScreen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.aritra.medsync.domain.extensions.runIO
import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.ui.screens.homeScreen.usecase.FetchMedicationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchMedicationUseCase: FetchMedicationUseCase
) : ViewModel() {

    var medicationModel by mutableStateOf(emptyList<Medication>())

    fun getMedications() = runIO {
        fetchMedicationUseCase.getAllMedications().collect { response ->
            medicationModel = response
        }
    }
}