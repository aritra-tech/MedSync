package com.aritra.medsync.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.aritra.medsync.R
import com.aritra.medsync.components.CustomTopAppBar
import com.aritra.medsync.components.MedSyncButton
import com.aritra.medsync.ui.theme.bold28
import com.aritra.medsync.ui.theme.medium16
import com.aritra.medsync.ui.theme.medium18
import com.aritra.medsync.ui.theme.normal14

@Composable
fun AddMedication(
//    navigateBack: NavHostController
) {

    var medicineName by rememberSaveable { mutableStateOf("") }
    var pillsAmount by rememberSaveable { mutableStateOf("") }
    var pillsEndDate by rememberSaveable { mutableStateOf("") }
    var pillsFrequency by rememberSaveable { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopAppBar(title = "") {
//                navigateBack.popBackStack()
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = "Add Plan",
                style = bold28,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Pill Name",
                style = medium16,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(5.dp))

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
                text = "Amount & Frequency",
                style = medium16,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(5.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
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

                // TODO: Add drop-down to choose the frequency of the medicine taken

                OutlinedTextField(
                    modifier = Modifier.width(152.dp),
                    value = pillsFrequency,
                    onValueChange = { pillsFrequency = it },
                    placeholder = { Text(text = "Daily", style = medium18) },
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "How Long?",
                style = medium16,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(5.dp))

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
                text = "Reminder",
                style = medium16,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(5.dp))

            Spacer(modifier = Modifier.weight(1f))
             MedSyncButton(
                 modifier = Modifier.fillMaxWidth(),
                 text = "Done"
             ) {
                 
             }
        }
    }
}

@Preview
@Composable
fun AddMedsScreenPreview() {
    AddMedication()
}