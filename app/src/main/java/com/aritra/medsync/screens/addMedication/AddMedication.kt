package com.aritra.medsync.screens.addMedication


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
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.aritra.medsync.R
import com.aritra.medsync.components.CustomTopAppBar
import com.aritra.medsync.components.MedSyncButton
import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.navigation.MedSyncScreens
import com.aritra.medsync.ui.theme.backgroundColor
import com.aritra.medsync.ui.theme.bold32
import com.aritra.medsync.ui.theme.medium16
import com.aritra.medsync.ui.theme.medium18
import com.aritra.medsync.ui.theme.normal14

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedication(
    navController: NavHostController,
    goToMedicationConfirmScreen: (List<Medication>) -> Unit,
    viewModel: AddMedicationViewModel
) {

    var medicineName by rememberSaveable { mutableStateOf("") }
    var pillsAmount by rememberSaveable { mutableStateOf("1") }
    var pillsEndDate by rememberSaveable { mutableStateOf("") }
    var pillsFrequency by rememberSaveable { mutableStateOf("") }
    var reminder by rememberSaveable { mutableStateOf("") }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopAppBar(
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

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = stringResource(R.string.pill_name),
                style = medium16,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = medicineName,
                onValueChange = { medicineName = it },
                placeholder = { Text(text = "Oxycodone") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.drugs_img),
                        contentDescription = "pill_image"
                    )
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(R.string.amount_frequency),
                style = medium16,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // TODO: Add drop-down to choose the frequency of the medicine taken

                OutlinedTextField(
                    modifier = Modifier.width(152.dp),
                    value = pillsAmount,
                    onValueChange = { pillsAmount = it },
                    placeholder = { Text(text = "2", style = medium18) },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.pills_img),
                            contentDescription = "pill_image"
                        )
                    },
                    trailingIcon = {
                        Text(
                            text = "pills",
                            style = normal14,
                            color = Color.Black
                        )
                    }
                )

                // TODO: Need to do some research regarding the frequency

                OutlinedTextField(
                    modifier = Modifier.width(152.dp),
                    value = pillsFrequency,
                    onValueChange = { pillsFrequency = it },
                    placeholder = { Text(text = "Daily", style = medium18) },
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(R.string.how_long),
                style = medium16,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = pillsEndDate,
                onValueChange = { pillsEndDate = it },
                placeholder = { Text(text = "20 th Feb, 2023") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.calendar_fill_img),
                        contentDescription = "end_date"
                    )
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(R.string.reminder),
                style = medium16,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(10.dp))

            // TODO: Need to change the leading icon

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = reminder,
                onValueChange = { reminder = it },
                placeholder = { Text(text = "11:00 AM") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.calendar_fill_img),
                        contentDescription = "end_date"
                    )
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            MedSyncButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Next"
            ) {
                addAndValidateMedication(
                    medicationName = medicineName,
                    pillsAmount = pillsAmount.toIntOrNull() ?: 0,
                    pillsFrequency = pillsFrequency,
                    goToConfirmMedicationScreen = {
                        goToMedicationConfirmScreen(it)
                    },
                    addMedicationViewModel = viewModel
                )
            }
        }
    }
}

fun addAndValidateMedication(
    medicationName: String,
    pillsAmount: Int,
    pillsFrequency: String,
    goToConfirmMedicationScreen: (List<Medication>) -> Unit,
    addMedicationViewModel: AddMedicationViewModel
) {
    // TODO : Validation required while saving

    val addMedication =
        addMedicationViewModel.createMedication(medicationName, pillsAmount, pillsFrequency)

    goToConfirmMedicationScreen(addMedication)
}
