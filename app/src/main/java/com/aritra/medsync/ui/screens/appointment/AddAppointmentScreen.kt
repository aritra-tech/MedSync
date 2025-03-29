package com.aritra.medsync.ui.screens.appointment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aritra.medsync.R
import com.aritra.medsync.components.MedSyncButton
import com.aritra.medsync.components.MedSyncTextField
import com.aritra.medsync.components.MedSyncTopAppBar
import com.aritra.medsync.ui.screens.appointment.state.AppointmentUiState
import com.aritra.medsync.ui.screens.appointment.viewModel.AppointmentViewModel
import com.aritra.medsync.ui.theme.PrimarySurface
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAppointmentScreen(
    navController: NavController,
    appointmentViewModel: AppointmentViewModel,
    modifier: Modifier = Modifier
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var doctorName by remember { mutableStateOf("") }
    var doctorSpecialization by remember { mutableStateOf("") }
    val uiState by appointmentViewModel.uiState.observeAsState(AppointmentUiState.Idle)

    LaunchedEffect(uiState) {
        when(uiState) {
            is AppointmentUiState.Success -> {
                doctorName = ""
                doctorSpecialization = ""
                navController.popBackStack()
            }
            is AppointmentUiState.Error -> {
                val errorMessage = (uiState as AppointmentUiState.Error).message
                scope.launch {
                    snackbarHostState.showSnackbar(message = errorMessage)
                }
            }
            else -> {}
        }
    }

    Scaffold(
        modifier = modifier.fillMaxWidth(),
        topBar = {
            MedSyncTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.add_appointment),
                colors = TopAppBarDefaults.topAppBarColors(PrimarySurface),
                onBackPress = { navController.popBackStack() }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                ) {
                    Text(data.visuals.message)
                }
            }
        }
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .background(PrimarySurface)
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MedSyncTextField(
             modifier = Modifier.fillMaxWidth(),
                headerText = stringResource(R.string.doctor_name),
                value = doctorName,
                onValueChange = {
                    doctorName = it
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            MedSyncTextField(
                modifier = Modifier.fillMaxWidth(),
                headerText = stringResource(R.string.doctor_specialization),
                value = doctorSpecialization,
                onValueChange = { doctorSpecialization = it }
            )

            Spacer(modifier = Modifier.weight(1f))

            MedSyncButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.save),
                onClick = {
                    appointmentViewModel.saveAppointments(doctorName, doctorSpecialization)
                    navController.popBackStack()
                }
            )
        }
    }
}