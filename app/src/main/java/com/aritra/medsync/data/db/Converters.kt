package com.aritra.medsync.data.db

import androidx.room.TypeConverter
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimeStamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimeStamp(date: Date?): Long? {
        return date?.time
    }
}