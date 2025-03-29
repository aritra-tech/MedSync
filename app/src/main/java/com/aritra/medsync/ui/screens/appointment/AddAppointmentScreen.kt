package com.aritra.medsync.ui.screens.appointment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableLongStateOf
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

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
    var appointmentDate by remember { mutableLongStateOf(0L) }
    var appointmentTime by remember { mutableLongStateOf(0L) }
    // Format for displaying date and time
    val dateFormatter = remember { SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()) }
    val timeFormatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }

    // Date Picker State
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    // Time Picker State
    var showTimePicker by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState()

    // Formatted date and time text for display
    val formattedDate = remember(appointmentDate) {
        if (appointmentDate > 0) dateFormatter.format(Date(appointmentDate))
        else ""
    }

    val formattedTime = remember(appointmentTime) {
        if (appointmentTime > 0) timeFormatter.format(Date(appointmentTime))
        else ""
    }
    val uiState by appointmentViewModel.uiState.observeAsState(AppointmentUiState.Idle)

    // Date Picker Dialog
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        appointmentDate = it
                    }
                    showDatePicker = false
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    // Time Picker Dialog
    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    // Convert hours and minutes to milliseconds
                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                    calendar.set(Calendar.MINUTE, timePickerState.minute)
                    calendar.set(Calendar.SECOND, 0)
                    calendar.set(Calendar.MILLISECOND, 0)
                    appointmentTime = calendar.timeInMillis
                    showTimePicker = false
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) {
                    Text("Cancel")
                }
            },
            title = { Text("Select Time") },
            text = { TimePicker(state = timePickerState) }
        )
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is AppointmentUiState.Success -> {
                doctorName = ""
                doctorSpecialization = ""
                appointmentDate = 0L
                appointmentTime = 0L
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

            Spacer(modifier = Modifier.height(20.dp))

            MedSyncTextField(
                modifier = Modifier.fillMaxWidth().clickable { showDatePicker = true },
                headerText = stringResource(R.string.appointment_date),
                value = formattedDate,
                onValueChange = { },
                readOnly = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            MedSyncTextField(
                modifier = Modifier.fillMaxWidth().clickable { showTimePicker = true },
                headerText = stringResource(R.string.appointment_time),
                value = formattedTime,
                onValueChange = { },
                readOnly = true
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