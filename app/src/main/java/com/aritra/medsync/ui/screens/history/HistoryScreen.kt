package com.aritra.medsync.ui.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aritra.medsync.R
import com.aritra.medsync.components.DatesHeader
import com.aritra.medsync.components.MedSyncEmptyState
import com.aritra.medsync.components.MedicationHistoryCard
import com.aritra.medsync.domain.model.CalendarModel
import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.ui.screens.history.viewmodel.HistoryViewModel
import com.aritra.medsync.ui.theme.PrimarySurface
import com.aritra.medsync.utils.CalendarDataSource
import com.aritra.medsync.utils.toFormattedDateString
import java.util.Date

@Composable
fun HistoryScreen(
    historyViewModel: HistoryViewModel
) {
    val medicationList = historyViewModel.historyModel
    val dataSource = CalendarDataSource()

    // Initialize with today's date
    var selectedDate by remember { mutableStateOf(dataSource.today) }

    // Filter medications based on selected date
    val filteredMedications = remember(medicationList, selectedDate) {
        medicationList.filter { medication ->
            medication.reminderTime.toFormattedDateString() == selectedDate.toFormattedDateString()
        }.sortedBy { it.reminderTime }
    }

    LaunchedEffect(Unit) {
        historyViewModel.loadMedicines()
    }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PrimarySurface)
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DatesHeader { dateModel ->
                // Update the selected date when user selects a date
                selectedDate = dateModel.date
            }

            Spacer(modifier = Modifier.height(15.dp))

            if (filteredMedications.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredMedications.size) { index ->
                        val med = filteredMedications[index]
                        MedicationHistoryCard(medication = med)
                    }
                }
            } else {
                MedSyncEmptyState(
                    stateTitle = if (selectedDate.toFormattedDateString() == dataSource.today.toFormattedDateString()) {
                        "No Medications Today"
                    } else {
                        "No Medications on ${selectedDate.toFormattedDateString()}"
                    },
                    stateDescription = "There are no medications recorded for this date",
                    R.raw.empty_box_animation
                )
            }
        }
    }
}