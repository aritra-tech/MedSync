package com.aritra.medsync.screens.homeScreen

import android.Manifest
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aritra.medsync.R
import com.aritra.medsync.components.MedSyncEmptyState
import com.aritra.medsync.components.MedSyncProgressCard
import com.aritra.medsync.components.MedicationCard
import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.screens.homeScreen.viewmodel.HomeViewModel
import com.aritra.medsync.ui.theme.OnPrimaryContainer
import com.aritra.medsync.ui.theme.PrimarySurface
import com.aritra.medsync.ui.theme.bold22
import com.aritra.medsync.ui.theme.medium24
import com.aritra.medsync.utils.Utils
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState


@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    onFabClicked: () -> Unit,
    navigateToUpdateScreen: (medicineID: Int) -> Unit,
    homeViewModel: HomeViewModel
) {

    val medication = homeViewModel.medicationModel
    val greetingText = Utils.greetingText()
    val postNotificationPermission = rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)

    LaunchedEffect(Unit) {
        homeViewModel.getMedications()
    }

    LaunchedEffect(Unit) {
        postNotificationPermission.launchPermissionRequest()
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
                .background(PrimarySurface)
                .padding(paddingValues)
                .padding(16.dp)
                .statusBarsPadding()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = greetingText,
                    style = bold22
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "Aritra",
                    style = medium24
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            MedSyncProgressCard(medication)

            Spacer(modifier = Modifier.height(30.dp))

            Medications(medication)

        }

    }
}
@Composable
fun Medications(medication: List<Medication>) {

    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.medications),
            style = medium24,
            color = OnPrimaryContainer
        )
    }

    Spacer(modifier = Modifier.height(15.dp))

    if (medication.isEmpty().not()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
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

