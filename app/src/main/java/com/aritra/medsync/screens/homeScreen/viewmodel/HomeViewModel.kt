package com.aritra.medsync.screens.homeScreen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aritra.medsync.domain.extensions.runIO
import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.screens.homeScreen.usecase.FetchMedicationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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