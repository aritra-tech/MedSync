package com.aritra.medsync.screens.homeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aritra.medsync.domain.state.HomeState
import com.aritra.medsync.screens.homeScreen.usecase.FetchMedicationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchMedicationUseCase: FetchMedicationUseCase
): ViewModel() {

    var homeState by mutableStateOf(HomeState())
        private set

    fun getMedications() {
        viewModelScope.launch {
            fetchMedicationUseCase.getAllMedications().onEach { medicationList ->
                homeState = homeState.copy(
                    medication = medicationList
                )
            }.launchIn(viewModelScope)
        }
    }
}