package com.aritra.medsync.utils

import com.aritra.medsync.domain.model.CalendarModel
import java.util.Calendar
import java.util.Date

class CalendarDataSource {
    val today: Date = Calendar.getInstance().time

    // Number of days to display at once
    private val totalDays = 7

    fun getData(startDate: Date? = null, lastSelectedDate: Date? = null): CalendarModel {
        val calendar = Calendar.getInstance()

        // If no start date specified, center around today
        if (startDate == null) {
            // Position calendar to show today in the middle
            calendar.time = today
            calendar.add(Calendar.DAY_OF_YEAR, -(totalDays / 2))
        } else {
            calendar.time = startDate
        }

        val dateList = mutableListOf<CalendarModel.DateModel>()
        val todayCalendar = Calendar.getInstance()
        val selectedCalendar = Calendar.getInstance().apply {
            time = lastSelectedDate ?: today
        }

        repeat(totalDays) {
            val date = calendar.time
            val isSelected = calendar.get(Calendar.YEAR) == selectedCalendar.get(Calendar.YEAR) &&
                    calendar.get(Calendar.MONTH) == selectedCalendar.get(Calendar.MONTH) &&
                    calendar.get(Calendar.DAY_OF_MONTH) == selectedCalendar.get(Calendar.DAY_OF_MONTH)

            val isToday = calendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR) &&
                    calendar.get(Calendar.MONTH) == todayCalendar.get(Calendar.MONTH) &&
                    calendar.get(Calendar.DAY_OF_MONTH) == todayCalendar.get(Calendar.DAY_OF_MONTH)

            dateList.add(
                CalendarModel.DateModel(
                    date = date,
                    isSelected = isSelected,
                    isToday = isToday
                )
            )
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        // Find the selected date, defaulting to today if none was selected
        val selectedDate = dateList.find { it.isSelected }
            ?: dateList.find { it.isToday }
            ?: dateList.first()

        return CalendarModel(
            selectedDate = selectedDate,
            visibleDates = dateList
        )
    }
}