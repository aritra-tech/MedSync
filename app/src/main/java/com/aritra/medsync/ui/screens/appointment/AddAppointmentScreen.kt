package com.aritra.medsync.ui.screens.appointment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aritra.medsync.R
import com.aritra.medsync.components.MedSyncButton
import com.aritra.medsync.components.MedSyncTopAppBar
import com.aritra.medsync.ui.screens.appointment.state.AppointmentUiState
import com.aritra.medsync.ui.screens.appointment.viewModel.AppointmentViewModel
import com.aritra.medsync.ui.theme.DMSansFontFamily
import com.aritra.medsync.ui.theme.PrimarySurface
import com.aritra.medsync.ui.theme.bold22
import com.aritra.medsync.ui.theme.normal18
import com.aritra.medsync.ui.theme.selectedBlue
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAppointmentScreen(
    navController: NavController,
    appointmentViewModel: AppointmentViewModel,
    modifier: Modifier = Modifier
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var doctorName by remember { mutableStateOf("") }
    var doctorSpecialization by remember { mutableStateOf("") }
    var appointmentDate by remember { mutableLongStateOf(0L) }
    var appointmentTime by remember { mutableLongStateOf(0L) }

    // Bottom sheet state for doctor specialization
    val sheetState = rememberModalBottomSheetState()
    var showSpecializationBottomSheet by remember { mutableStateOf(false) }

    val specializations = remember {
        listOf(
            "Cardiologist",
            "Dermatologist",
            "Endocrinologist",
            "Gastroenterologist",
            "Neurologist",
            "Obstetrician/Gynecologist",
            "Oncologist",
            "Ophthalmologist",
            "Orthopedist",
            "Pediatrician",
            "Psychiatrist",
            "Pulmonologist",
            "Rheumatologist",
            "Urologist"
        )
    }

    // Set IST timezone for formatting
    val istTimeZone = TimeZone.getTimeZone("Asia/Kolkata")
    val dateFormatter = remember { SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).apply { timeZone = istTimeZone } }
    val timeFormatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()).apply { timeZone = istTimeZone } }

    // Date Picker State
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    // Time Picker State
    var showTimePicker by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState()

    // Formatted date and time text for display in IST
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
                    datePickerState.selectedDateMillis?.let { millis ->
                        val calendar = Calendar.getInstance(istTimeZone).apply {
                            timeInMillis = millis
                            set(Calendar.HOUR_OF_DAY, 0)
                            set(Calendar.MINUTE, 0)
                            set(Calendar.SECOND, 0)
                            set(Calendar.MILLISECOND, 0)
                        }
                        appointmentDate = calendar.timeInMillis
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
                    val calendar = Calendar.getInstance(istTimeZone).apply {
                        set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                        set(Calendar.MINUTE, timePickerState.minute)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)
                    }
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

    if (showSpecializationBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSpecializationBottomSheet = false },
            sheetState = sheetState,
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.select_doctor_specialization),
                    style = bold22.copy(Color.Black),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                specializations.forEach { specialization ->
                    Text(
                        text = specialization,
                        style = normal18.copy(Color.Black),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                doctorSpecialization = specialization
                                showSpecializationBottomSheet = false
                            }
                            .padding(vertical = 12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
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
                    snackBarHostState.showSnackbar(message = errorMessage)
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
            SnackbarHost(hostState = snackBarHostState) { data ->
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
            // Doctor Name Field
            Column(Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.doctor_name),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = DMSansFontFamily,
                        fontWeight = FontWeight(500),
                        color = Color.Black,
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = doctorName,
                    onValueChange = { doctorName = it },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontFamily = DMSansFontFamily,
                        fontSize = 16.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Doctor Specialization Field
            Column(Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.doctor_specialization),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = DMSansFontFamily,
                        fontWeight = FontWeight(500),
                        color = Color.Black,
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showSpecializationBottomSheet = true }
                ) {
                    OutlinedTextField(
                        value = doctorSpecialization,
                        onValueChange = { doctorSpecialization = it },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        readOnly = true,
                        enabled = false,
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontFamily = DMSansFontFamily,
                            fontSize = 16.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Date Picker Field
            Column(Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.appointment_date),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = DMSansFontFamily,
                        fontWeight = FontWeight(500),
                        color = Color.Black,
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDatePicker = true }
                ) {
                    OutlinedTextField(
                        value = formattedDate,
                        onValueChange = { },
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        enabled = false,
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontFamily = DMSansFontFamily,
                            fontSize = 16.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Time Picker Field
            Column(Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.appointment_time),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = DMSansFontFamily,
                        fontWeight = FontWeight(500),
                        color = Color.Black,
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showTimePicker = true }
                ) {
                    OutlinedTextField(
                        value = formattedTime,
                        onValueChange = { },
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        enabled = false,
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontFamily = DMSansFontFamily,
                            fontSize = 16.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            MedSyncButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.save),
                buttonColor = selectedBlue,
                onClick = {
                    val date = if (appointmentDate > 0L) Date(appointmentDate) else null
                    val time = if (appointmentTime > 0L) Date(appointmentTime) else null
                    appointmentViewModel.saveAppointments(
                        doctorName = doctorName,
                        doctorSpecialization = doctorSpecialization,
                        appointmentDate = date,
                        appointmentTime = time
                    )
                    navController.popBackStack()
                }
            )
        }
    }
}