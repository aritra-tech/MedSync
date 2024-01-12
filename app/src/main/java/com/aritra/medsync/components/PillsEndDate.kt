package com.aritra.medsync.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.aritra.medsync.R
import com.aritra.medsync.utils.toFormattedDateString
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PillsEndDate(endDate: (Long) -> Unit) {

    var shouldShowDatePicker by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val isClicked: Boolean by interactionSource.collectIsPressedAsState()

    if (isClicked) {
        shouldShowDatePicker = true
    }

    val today = Calendar.getInstance()
    today.set(Calendar.HOUR_OF_DAY, 0)
    today.set(Calendar.MINUTE, 0)
    today.set(Calendar.SECOND, 0)
    today.set(Calendar.MILLISECOND, 0)
    val currentDayMillis = today.timeInMillis
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis(),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis >= currentDayMillis
            }
        }
    )

    var selectedDate by rememberSaveable {
        mutableStateOf(
            datePickerState.selectedDateMillis?.toFormattedDateString() ?: ""
        )
    }

    EndDatePicker(
        state = datePickerState,
        shouldDisplay = shouldShowDatePicker,
        onConfirmClicked = { selectedDateInMillis ->
            selectedDate = selectedDateInMillis.toFormattedDateString()
            endDate(selectedDateInMillis)
        },
        dismissRequest = {
            shouldShowDatePicker = false
        }
    )

    MedSyncTextField(
        modifier = Modifier.fillMaxWidth(),
        readOnly = true,
        headerText = stringResource(id = R.string.how_long),
        hintText = stringResource(R.string.end_date_hint),
        value = selectedDate,
        onValueChange = {},
        interactionSource = interactionSource
    )
}