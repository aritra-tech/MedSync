package com.aritra.medsync.domain.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class CalendarModel(
    val selectedDate: DateModel,
    val visibleDates: List<DateModel>
) {

    val startDate: DateModel = visibleDates.first()
    val endDate: DateModel = visibleDates.last()

    data class DateModel(
        val date: Date,
        val isSelected: Boolean,
        val isToday: Boolean
    ) {
        val day : String = SimpleDateFormat("E", Locale.getDefault()).format(date)
    }
}
