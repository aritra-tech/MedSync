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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aritra.medsync.R
import com.aritra.medsync.components.MedSyncEmptyState
import com.aritra.medsync.components.MedSyncProgressCard
import com.aritra.medsync.components.MedicationCard
import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.domain.state.HomeState
import com.aritra.medsync.ui.theme.OnPrimaryContainer
import com.aritra.medsync.ui.theme.Primary
import com.aritra.medsync.ui.theme.PrimarySurface
import com.aritra.medsync.ui.theme.bold20
import com.aritra.medsync.ui.theme.bold24
import java.time.LocalTime
import java.time.ZoneId


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    onFabClicked: () -> Unit,
    navigateToUpdateScreen: (medicineID: Int) -> Unit,
    homeViewModel: HomeViewModel
) {

    val state = homeViewModel.homeState

    val greetingText = when (LocalTime.now(ZoneId.systemDefault()).hour) {
        in 5..11 -> "Good morning,"
        in 12..16 -> "Good afternoon,"
        in 17..20 -> "Good evening,"
        else -> "Good night,"
    }

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

            Medications(state)

        }

    }
}
@Composable
fun Medications(state: HomeState) {

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

        Text(
            text = "Edit",
            style = bold20,
            color = Primary
        )
    }

    Spacer(modifier = Modifier.height(10.dp))

    var medicationList: List<Medication> by remember {
        mutableStateOf(emptyList())
    }
    medicationList = state.medication

    if (medicationList.isEmpty().not()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(medicationList.size) { index ->
                val medication = medicationList[index]
                MedicationCard(medication = medication)
            }
        }
    } else {
        MedSyncEmptyState(stateTitle = "", stateDescription = "", R.raw.empty_box_animation)
    }
}

