package com.aritra.medsync.screens.history

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aritra.medsync.R
import com.aritra.medsync.components.MedSyncEmptyState
import com.aritra.medsync.components.MedicationCard
import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.screens.history.viewmodel.HistoryViewModel
import com.aritra.medsync.ui.theme.PrimarySurface
import com.aritra.medsync.utils.hasPassed

@Composable
fun HistoryScreen(
    historyViewModel: HistoryViewModel
) {
    val medicationList = historyViewModel.historyModel

    LaunchedEffect(Unit) {
        historyViewModel.loadMedicines()
    }

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(PrimarySurface),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SortedMedicationList(medicationList)
        }
    }
}

@Composable
fun SortedMedicationList(medicationList: List<Medication>) {

    val filteredList = medicationList.filter {
        it.reminderTime.hasPassed()
    }

    // TODO: fix the DatesHeader
//    DatesHeader { selectedDate ->
//        val newMedicationList = medicationList
//            .filter { medication ->
//            medication.reminderTime.toFormattedDateString() == selectedDate.date.toFormattedDateString()
//        }
//            .sortedBy { it.reminderTime }
//
//        filteredList = newMedicationList
//    }

    Spacer(modifier = Modifier.height(15.dp))

    if (filteredList.isEmpty().not()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredList.size) { index ->
                val med = filteredList[index]
                MedicationCard(medication = med)
            }
        }
    } else {
        MedSyncEmptyState(stateTitle = "", stateDescription = "", R.raw.empty_box_animation)
    }
}
