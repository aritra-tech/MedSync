package com.aritra.medsync.screens.homeScreen

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aritra.medsync.R
import com.aritra.medsync.components.MedSyncEmptyState
import com.aritra.medsync.components.MedSyncProgressCard
import com.aritra.medsync.components.MedicationCard
import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.ui.theme.OnPrimaryContainer
import com.aritra.medsync.ui.theme.PrimarySurface
import com.aritra.medsync.ui.theme.bold20
import com.aritra.medsync.ui.theme.bold24
import com.aritra.medsync.utils.Utils


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    onFabClicked: () -> Unit,
    navigateToUpdateScreen: (medicineID: Int) -> Unit,
    homeViewModel: HomeViewModel
) {

    val medication = homeViewModel.medicationModel
    val greetingText = Utils.greetingText()

    LaunchedEffect(Unit) {
        homeViewModel.getMedications()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(vertical = 90.dp),
                onClick = { onFabClicked() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Add Meds"
                )
            }
        },
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(PrimarySurface)
        ) {
            Column {
                Text(
                    modifier = Modifier.padding(top = 10.dp, start = 16.dp),
                    text = greetingText,
                    style = bold24
                )
                Text(
                    modifier = Modifier.padding(start = 12.dp),
                    text = "Aritra!",
                    style = bold20
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            MedSyncProgressCard()

            Spacer(modifier = Modifier.height(20.dp))

            Medications(medication)

        }

    }
}
@Composable
fun Medications(medication: List<Medication>) {

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "To take",
            style = bold20,
            color = OnPrimaryContainer
        )
    }

    Spacer(modifier = Modifier.height(10.dp))

    if (medication.isEmpty().not()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(medication.size) { index ->
                val med = medication[index]
                MedicationCard(medication = med)
            }
        }
    } else {
        MedSyncEmptyState(stateTitle = "", stateDescription = "", R.raw.empty_box_animation)
    }
}

