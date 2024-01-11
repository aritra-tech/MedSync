package com.aritra.medsync.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.aritra.medsync.utils.CalendarInformation
import java.util.Calendar

@Composable
fun MedSyncReminderTextField(
    time: (CalendarInformation) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()
    val currentTime = CalendarInformation(Calendar.getInstance())
    var selectedTime by rememberSaveable(
        stateSaver = CalendarInformation.getStateSaver()
    ) {
        mutableStateOf(currentTime)
    }


    MedSyncTimePicker(
        showDialog = isPressed,
        selectedDate = selectedTime,
        onSelectedTime = {
            selectedTime = it
            time(it)
        }
    )


    MedSyncTextField(
        modifier = Modifier.fillMaxWidth(),
        readOnly = true,
        value = selectedTime.getDateFormatted("h:mm a"),
        onValueChange = {},
        interactionSource = interactionSource
    )
}