package com.aritra.medsync.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.util.Date

@Parcelize
@Entity(tableName = "medication")
data class Medication(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var medicineName : String = "",
    var pillsAmount : Int = 0,
    var pillsFrequency : String = "",
    var reminderTime : LocalDateTime? = null,
    var date : Date? = null
) : Parcelable