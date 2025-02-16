package com.aritra.medsync.ui.screens.homeScreen

import android.Manifest
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.aritra.medsync.ui.screens.homeScreen.viewmodel.HomeViewModel
import com.aritra.medsync.ui.screens.intro.UserData
import com.aritra.medsync.ui.theme.OnPrimaryContainer
import com.aritra.medsync.ui.theme.PrimarySurface
import com.aritra.medsync.ui.theme.bold22
import com.aritra.medsync.ui.theme.medium24
import com.aritra.medsync.utils.Utils
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    userData: UserData?,
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PrimarySurface)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = greetingText,
                    style = bold22
                )

                Spacer(modifier = Modifier.width(4.dp))

                if (userData?.username != null) {
                    Text(
                        text = userData.username,
                        style = medium24
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            MedSyncProgressCard(medication)

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = stringResource(R.string.medications),
                style = medium24,
                color = OnPrimaryContainer
            )

            Spacer(modifier = Modifier.height(15.dp))

            if (medication.isEmpty().not()) {
                Column(
                    modifier = Modifier.padding(bottom = 90.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    medication.forEach { med ->
                        MedicationCard(medication = med)
                    }
                }
            } else {
                MedSyncEmptyState(
                    stateTitle = "",
                    stateDescription = "",
                    R.raw.empty_box_animation
                )
            }
        }
    }
}

