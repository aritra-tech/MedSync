package com.aritra.medsync.screens.history.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.aritra.medsync.domain.extensions.runIO
import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.screens.homeScreen.usecase.FetchMedicationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val fetchMedicationUseCase: FetchMedicationUseCase
): ViewModel() {

    var historyModel by mutableStateOf(emptyList<Medication>())

    fun loadMedicines() = runIO {
        fetchMedicationUseCase.getAllMedications().collect {response ->
            historyModel = response
        }
    }

}