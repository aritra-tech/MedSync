package com.aritra.medsync.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.aritra.medsync.R
import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.ui.theme.MedicineCircleColor
import com.aritra.medsync.ui.theme.OnPrimaryContainer
import com.aritra.medsync.ui.theme.OnSurface60
import com.aritra.medsync.ui.theme.bold18
import com.aritra.medsync.ui.theme.bold24
import com.aritra.medsync.ui.theme.dividerColor
import com.aritra.medsync.ui.theme.medium14
import com.aritra.medsync.utils.Utils.getMedicineUnit
import com.aritra.medsync.utils.toFormattedTimeString

@Composable
fun MedicationCard(
    medication: Medication
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = medication.reminderTime.toFormattedTimeString(),
                style = bold24.copy(color = OnPrimaryContainer)
            )

            Spacer(modifier = Modifier.height(10.dp))

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = dividerColor,
                thickness = 2.dp
            )

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MedicineCircleColor)
                        .padding(10.dp),
                    painter = getMedicineImage(medication.medicineType),
                    contentDescription = null
                )

                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = medication.medicineName,
                        style = bold18.copy(color = OnPrimaryContainer)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "${medication.pillsAmount} ${getMedicineUnit(medication.medicineType)} | ${medication.pillsFrequency}",
                        style = medium14.copy(color = OnSurface60)
                    )
                }
            }
        }
    }
}

@Composable
fun getMedicineImage(medicineType: String): Painter {
    return when(medicineType) {
        "TABLET" -> painterResource(id = R.drawable.pill)
        "CAPSULE" -> painterResource(id = R.drawable.capsule)
        "SYRUP" -> painterResource(id = R.drawable.amp)
        "INHALER" -> painterResource(id = R.drawable.inahler)
        else -> painterResource(id = R.drawable.ic_launcher_foreground) // TODO: Need to change the image
    }
}
