package com.aritra.medsync.screens

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aritra.medsync.utils.Days
import com.aritra.medsync.utils.toFormatted
import com.aritra.medsync.utils.toMonthName
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedication(
    navigateBack: () -> Unit
) {

    var medicineName by rememberSaveable { mutableStateOf("") }
    var medicineNotes by rememberSaveable { mutableStateOf("") }
    var medicineInstructions by rememberSaveable { mutableStateOf("") }
    var endDate by rememberSaveable { mutableLongStateOf(Date().time) }
    var isMonday by rememberSaveable { mutableStateOf(false) }
    var isTuesday by rememberSaveable { mutableStateOf(false) }
    var isWednesday by rememberSaveable { mutableStateOf(false) }
    var isThursday by rememberSaveable { mutableStateOf(false) }
    var isFriday by rememberSaveable { mutableStateOf(false) }
    var isSaturday by rememberSaveable { mutableStateOf(false) }
    var isSunday by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = medicineName,
            onValueChange = { medicineName = it },
            label = { Text(text = "Medicine Name") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = medicineNotes,
            onValueChange = { medicineNotes = it },
            label = { Text(text = "Notes") }
        )
        EndDate { endDate = it }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Days",
            style = MaterialTheme.typography.bodyLarge
        )

        var daysCount by rememberSaveable { mutableIntStateOf(0) }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                selected = isMonday,
                onClick = {
                    handleChipSelection(
                        isSelected = isMonday,
                        selectionCount = daysCount,
                        onStateChange = { count, selected ->
                            isMonday = selected
                            daysCount = count
                        },
                        onShowMaxSelectionError = {
                            showSelectionDaysToast(context)
                        }
                    )
                },
                label = {
                    Text(text = Days.Monday.name)
                },
                leadingIcon = if (isMonday) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                        )
                    }
                } else {
                    null
                }
            )
            FilterChip(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                selected = isTuesday,
                onClick = {
                    handleChipSelection(
                        isSelected = isTuesday,
                        selectionCount = daysCount,
                        onStateChange = { count, selected ->
                            isTuesday = selected
                            daysCount = count
                        },
                        onShowMaxSelectionError = {
                            showSelectionDaysToast(context)
                        }
                    )
                },
                label = {
                    Text(text = Days.Tuesday.name)
                },
                leadingIcon = if (isTuesday) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                        )
                    }
                } else {
                    null
                }
            )
            FilterChip(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                selected = isWednesday,
                onClick = {
                    handleChipSelection(
                        isSelected = isWednesday,
                        selectionCount = daysCount,
                        onStateChange = { count, selected ->
                            isWednesday = selected
                            daysCount = count
                        },
                        onShowMaxSelectionError = {
                            showSelectionDaysToast(context)
                        }
                    )
                },
                label = {
                    Text(text = Days.Wednesday.name)
                },
                leadingIcon = if (isWednesday) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                        )
                    }
                } else {
                    null
                }
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                selected = isThursday,
                onClick = {
                    handleChipSelection(
                        isSelected = isThursday,
                        selectionCount = daysCount,
                        onStateChange = { count, selected ->
                            isThursday = selected
                            daysCount = count
                        },
                        onShowMaxSelectionError = {
                            showSelectionDaysToast(context)
                        }
                    )
                },
                label = {
                    Text(text = Days.Thursday.name)
                },
                leadingIcon = if (isThursday) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                        )
                    }
                } else {
                    null
                }
            )
            FilterChip(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                selected = isFriday,
                onClick = {
                    handleChipSelection(
                        isSelected = isFriday,
                        selectionCount = daysCount,
                        onStateChange = { count, selected ->
                            isFriday = selected
                            daysCount = count
                        },
                        onShowMaxSelectionError = {
                            showSelectionDaysToast(context)
                        }
                    )
                },
                label = {
                    Text(text = Days.Friday.name)
                },
                leadingIcon = if (isFriday) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                        )
                    }
                } else {
                    null
                }
            )
            FilterChip(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                selected = isSaturday,
                onClick = {
                    handleChipSelection(
                        isSelected = isSaturday,
                        selectionCount = daysCount,
                        onStateChange = { count, selected ->
                            isSaturday = selected
                            daysCount = count
                        },
                        onShowMaxSelectionError = {
                            showSelectionDaysToast(context)
                        }
                    )
                },
                label = {
                    Text(text = Days.Saturday.name)
                },
                leadingIcon = if (isSaturday) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                        )
                    }
                } else {
                    null
                }
            )
            FilterChip(
                modifier = Modifier
                    .weight(2f),
                selected = isSunday,
                onClick = {
                    handleChipSelection(
                        isSelected = isSunday,
                        selectionCount = daysCount,
                        onStateChange = { count, selected ->
                            isSunday = selected
                            daysCount = count
                        },
                        onShowMaxSelectionError = {
                            showSelectionDaysToast(context)
                        }
                    )
                },
                label = {
                    Text(text = Days.Sunday.name)
                },
                leadingIcon = if (isSunday) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                        )
                    }
                } else {
                    null
                }
            )
        }
    }

}

private fun showSelectionDaysToast(context: Context) {
    Toast.makeText(
        context,
        "Please select the Weekdays.",
        Toast.LENGTH_LONG
    ).show()
}

fun handleChipSelection(
    isSelected: Boolean,
    selectionCount: Int,
    onStateChange: (Int, Boolean) -> Unit,
    onShowMaxSelectionError: () -> Unit
) {
    if (isSelected) {
        onStateChange(selectionCount - 1, !isSelected)
    } else {
        onShowMaxSelectionError()
    }

}

@Composable
fun EndDate(endDate: (Long) -> Unit) {

    val source = remember { MutableInteractionSource() }
    val isPressed: Boolean by source.collectIsPressedAsState()

    val currentDate = Date().toFormatted()
    var selectedDate by remember { mutableStateOf(currentDate) }

    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val year: Int = calendar.get(Calendar.YEAR)
    val month: Int = calendar.get(Calendar.MONTH)
    val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val datePicker = DatePickerDialog(
        context, { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            val newDate = Calendar.getInstance()
            newDate.set(year, selectedMonth, selectedDayOfMonth)
            selectedDate = "$selectedDayOfMonth ${month.toMonthName()}, $selectedYear"
            endDate(newDate.timeInMillis)
        }, year, month, day
    )

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        readOnly = true,
        value = selectedDate,
        onValueChange = { },
        label = { Text(text = "End Date") },
        interactionSource = source
    )

    if (isPressed) {
        datePicker.show()
    }

}

@Preview(showBackground = true)
@Composable
fun AddMedicationScreen() {
    AddMedication(navigateBack = {})
}