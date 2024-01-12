package com.aritra.medsync.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aritra.medsync.ui.theme.OnSurface60
import com.aritra.medsync.ui.theme.bold16

@Composable
fun MedicationDetailsInvoice(
    attribute: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = attribute,
            color = OnSurface60,
            style = bold16
        )

        Text(
            text = value,
            color = OnSurface60,
            style = bold16
        )

    }
}