package com.aritra.medsync.domain.model

import java.util.Date

data class Appointment(
    val id: String = "",
    val doctorName: String = "",
    val doctorSpecialization: String = "",
    val appointmentDate: String = "",
    val appointmentTime: String = ""
)