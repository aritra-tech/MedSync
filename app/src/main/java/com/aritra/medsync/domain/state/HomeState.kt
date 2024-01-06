package com.aritra.medsync.domain.state

import com.aritra.medsync.domain.model.Medication

data class HomeState(
    val medication : List<Medication> = emptyList()
)
