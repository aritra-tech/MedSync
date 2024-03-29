package com.aritra.medsync.screens.addMedication


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.aritra.medsync.R
import com.aritra.medsync.components.MedSyncButton
import com.aritra.medsync.components.MedSyncReminderTextField
import com.aritra.medsync.components.MedSyncTextField
import com.aritra.medsync.components.MedSyncTopAppBar
import com.aritra.medsync.components.MedicineTypeCard
import com.aritra.medsync.components.PillsEndDate
import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.domain.model.MedicineType
import com.aritra.medsync.ui.theme.OnPrimaryContainer
import com.aritra.medsync.ui.theme.backgroundColor
import com.aritra.medsync.ui.theme.bold14
import com.aritra.medsync.ui.theme.bold32
import com.aritra.medsync.ui.theme.medium16
import com.aritra.medsync.utils.CalendarInformation
import java.util.Calendar
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedication(
    navController: NavHostController,
    goToMedicationConfirmScreen: (List<Medication>) -> Unit,
    viewModel: AddMedicationViewModel
) {

    var medicineName by rememberSaveable { mutableStateOf("") }
    var pillsAmount by rememberSaveable { mutableStateOf("") }
    var pillsEndDate by rememberSaveable { mutableLongStateOf(Date().time) }
    var pillsFrequency by rememberSaveable { mutableStateOf("") }
    val selectedTimes = rememberSaveable(
        saver = CalendarInformation.getStateListSaver()
    ) {
        mutableStateListOf(CalendarInformation(Calendar.getInstance()))
    }
    var selectedMedicineType by rememberSaveable {
        mutableStateOf(MedicineType.TABLET)
    }
    var isButtonEnabled by rememberSaveable { mutableStateOf(false) }
    var suffixText by rememberSaveable { mutableStateOf("Tablet") }

    fun checkAllFieldsFilled() {
        isButtonEnabled = (
                medicineName.isNotBlank() &&
                        pillsAmount.isNotBlank() &&
                        pillsFrequency.isNotBlank()
                )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MedSyncTopAppBar(
                title = "",
                colors = TopAppBarDefaults.topAppBarColors(backgroundColor)
            ) {
                navController.popBackStack()
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(backgroundColor)
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = stringResource(R.string.add_plan),
                style = bold32,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                MedicineTypeCard(
                    image = painterResource(id = R.drawable.pill),
                    isSelected = MedicineType.TABLET == selectedMedicineType,
                    medicineType = MedicineType.TABLET,
                    onClick = {
                        selectedMedicineType = it
                        suffixText = "Tablet"
                    }
                )
                MedicineTypeCard(
                    image = painterResource(id = R.drawable.capsule),
                    isSelected = MedicineType.CAPSULE == selectedMedicineType,
                    medicineType = MedicineType.CAPSULE,
                    onClick = {
                        selectedMedicineType = it
                        suffixText = "Capsule"
                    }
                )
                MedicineTypeCard(
                    image = painterResource(id = R.drawable.amp),
                    isSelected = MedicineType.SYRUP == selectedMedicineType,
                    medicineType = MedicineType.SYRUP,
                    onClick = {
                        selectedMedicineType = it
                        suffixText = "mL"
                    }
                )
                MedicineTypeCard(
                    image = painterResource(id = R.drawable.inahler),
                    isSelected = MedicineType.INHALER == selectedMedicineType,
                    medicineType = MedicineType.INHALER,
                    onClick = {
                        selectedMedicineType = it
                        suffixText = "Puffs"
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            MedSyncTextField(
                modifier = Modifier.fillMaxWidth(),
                headerText = stringResource(R.string.pill_name),
                hintText = stringResource(R.string.pills_name_hint),
                value = medicineName,
                keyboardType = KeyboardType.Text,
                onValueChange = {
                    medicineName = it
                    checkAllFieldsFilled()
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(R.string.amount_frequency),
                style = medium16,
                color = OnPrimaryContainer
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                MedSyncTextField(
                    hintText = stringResource(R.string.pills_amount_hint),
                    modifier = Modifier.width(152.dp),
                    value = pillsAmount,
                    onValueChange = {
                        pillsAmount = it
                        checkAllFieldsFilled()
                    },
                    trailingIcon = {
                        Text(
                            modifier = Modifier.padding(end = 12.dp),
                            text = suffixText,
                            style = bold14,
                            color = Color.Black
                        )
                    }
                )
                // TODO: Need to do some research regarding the frequency
                MedSyncTextField(
                    modifier = Modifier.width(152.dp),
                    hintText = stringResource(R.string.pills_frequency_hint),
                    value = pillsFrequency,
                    onValueChange = {
                        pillsFrequency = it
                        checkAllFieldsFilled()
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            PillsEndDate { pillsEndDate = it }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(id = R.string.reminder),
                style = medium16,
                color = OnPrimaryContainer
            )
            Spacer(modifier = Modifier.height(10.dp))

            for (time in selectedTimes.indices) {
                MedSyncReminderTextField(
                    time = {
                        selectedTimes[time] = it
                    }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            MedSyncButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Next",
                enabled = isButtonEnabled
            ) {
                addAndValidateMedication(
                    medicationName = medicineName,
                    pillsAmount = pillsAmount,
                    pillsFrequency = pillsFrequency,
                    endDate = pillsEndDate,
                    reminder = selectedTimes,
                    medicineType = MedicineType.getMedicineTypeString(selectedMedicineType),
                    addMedicationViewModel = viewModel
                ) {
                    goToMedicationConfirmScreen(it)

                }
            }
        }
    }
}

fun addAndValidateMedication(
    medicationName: String,
    pillsAmount: String,
    pillsFrequency: String,
    endDate: Long,
    medicineType: String,
    reminder: List<CalendarInformation>,
    addMedicationViewModel: AddMedicationViewModel,
    goToConfirmMedicationScreen: (List<Medication>) -> Unit,
) {

    val addMedication =
        addMedicationViewModel.createMedication(
            medicationName,
            pillsAmount,
            pillsFrequency,
            Date(endDate),
            reminder,
            medicineType
        )

    goToConfirmMedicationScreen(addMedication)
}
