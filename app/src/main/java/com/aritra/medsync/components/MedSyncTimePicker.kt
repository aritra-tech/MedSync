package com.aritra.medsync.components

import android.app.TimePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.aritra.medsync.utils.CalendarInformation
import java.util.Calendar

@Composable
fun MedSyncTimePicker(
    showDialog: Boolean,
    selectedDate: CalendarInformation,
    onSelectedTime: (selectedDate: CalendarInformation) -> Unit
) {
    val listener = setUpOnTimeSetListener(onSelectedTime)
    val timePickerDialog = getTimePickerDialog(selectedDate, listener)
    if (showDialog) {
        timePickerDialog.show()
    }
}

private fun setUpOnTimeSetListener(
    onSelectedTime: (selectedDate: CalendarInformation) -> Unit
): TimePickerDialog.OnTimeSetListener {
    return TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
        val newDate = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }
        onSelectedTime(CalendarInformation(newDate))
    }
}

@Composable
private fun getTimePickerDialog(
    selectedDate: CalendarInformation,
    listener: TimePickerDialog.OnTimeSetListener
): TimePickerDialog {
    val context = LocalContext.current
    val (hour, minute) = selectedDate.dateInformation
    return TimePickerDialog(
        context,
        listener,
        hour,
        minute,
        false
    )
}