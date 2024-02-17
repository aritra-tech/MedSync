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
import com.aritra.medsync.screens.homeScreen.viewmodel.HomeViewModel
import com.aritra.medsync.ui.theme.OnPrimaryContainer
import com.aritra.medsync.ui.theme.PrimarySurface
import com.aritra.medsync.ui.theme.extraBold28
import com.aritra.medsync.ui.theme.medium20
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
                .padding(paddingValues)
                .background(PrimarySurface)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Text(
                    text = greetingText,
                    style = extraBold28
                )
                Text(
                    text = "Aritra!",
                    style = medium24
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            MedSyncProgressCard(medication)

            Spacer(modifier = Modifier.height(20.dp))

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Medications",
                    style = medium20,
                    color = OnPrimaryContainer
                )
            }


            if (medication.isEmpty().not()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(medication.size) { index ->
                        val med = medication[index]
                        MedicationCard(medication = med)
                    }
                }
            } else {
                MedSyncEmptyState(stateTitle = "", R.raw.empty_box_animation)
            }
        }
    }
}
