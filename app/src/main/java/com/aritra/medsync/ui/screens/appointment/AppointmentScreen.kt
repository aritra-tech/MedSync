package com.aritra.medsync.ui.screens.appointment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aritra.medsync.R
import com.aritra.medsync.components.MedSyncEmptyState
import com.aritra.medsync.ui.screens.appointment.component.AppointmentCard
import com.aritra.medsync.ui.screens.appointment.viewModel.AppointmentViewModel
import com.aritra.medsync.ui.theme.OnPrimaryContainer
import com.aritra.medsync.ui.theme.PrimarySurface
import com.aritra.medsync.ui.theme.bold20
import com.aritra.medsync.utils.Utils.formatDateHeader

@Composable
fun AppointmentScreen(
    onFabClicked: () -> Unit,
    appointmentViewModel: AppointmentViewModel
) {
    val appointments by appointmentViewModel.appointments.observeAsState(emptyMap())

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier.padding(vertical = 90.dp),
                onClick = { onFabClicked() },
                icon = { Icon(imageVector = Icons.Outlined.Add, contentDescription = "Add Appointments") },
                text = { Text(text = "Add appointment") }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PrimarySurface)
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 80.dp)
                .verticalScroll(rememberScrollState())
        ) {
            if (appointments.isEmpty().not()) {
                appointments.forEach { (date, appointmentList) ->
                    Text(
                        modifier = Modifier.padding(vertical = 10.dp),
                        text = formatDateHeader(date),
                        style = bold20.copy(OnPrimaryContainer),
                    )

                    appointmentList.forEach { appointment ->
                        AppointmentCard(appointment)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            } else {
                MedSyncEmptyState(
                    stateTitle = "No appointments are booked",
                    stateDescription = "",
                    R.raw.empty_box_animation
                )
            }
        }
    }
}
