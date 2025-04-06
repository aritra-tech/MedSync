package com.aritra.medsync.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aritra.medsync.domain.model.CalendarModel
import com.aritra.medsync.utils.CalendarDataSource
import com.aritra.medsync.utils.toFormattedDateString
import java.util.Calendar
import java.util.Date

@Composable
fun DatesHeader(
    onDateSelected: (CalendarModel.DateModel) -> Unit
) {
    val dataSource = CalendarDataSource()
    // Initialize with today highlighted
    var calendarModel by remember {
        mutableStateOf(dataSource.getData(startDate = null, lastSelectedDate = dataSource.today))
    }

    // Trigger initial selection with today's date
    LaunchedEffect(Unit) {
        // Find today's date model in the visible dates
        val todayModel = calendarModel.visibleDates.find { it.isToday } ?: calendarModel.selectedDate
        onDateSelected(todayModel)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        DateHeader(
            data = calendarModel,
            onPrevClick = { startDate ->
                val calendar = Calendar.getInstance()
                calendar.time = startDate
                calendar.add(Calendar.DAY_OF_YEAR, -2)
                val finalDate = calendar.time
                calendarModel = dataSource.getData(startDate = finalDate, lastSelectedDate = calendarModel.selectedDate.date)
            },
            onNextClick = { endDate ->
                val calendar = Calendar.getInstance()
                calendar.time = endDate
                calendar.add(Calendar.DAY_OF_YEAR, 2)
                val finalDate = calendar.time
                calendarModel = dataSource.getData(startDate = finalDate, lastSelectedDate = calendarModel.selectedDate.date)
            }
        )
        DateList(
            data = calendarModel,
            onDateClicked = { date ->
                calendarModel = calendarModel.copy(
                    selectedDate = date,
                    visibleDates = calendarModel.visibleDates.map {
                        it.copy(
                            isSelected = it.date.toFormattedDateString() == date.date.toFormattedDateString()
                        )
                    }
                )
                onDateSelected(date)
            }
        )
    }
}