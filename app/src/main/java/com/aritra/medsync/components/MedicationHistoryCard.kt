package com.aritra.medsync.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aritra.medsync.R
import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.ui.theme.MedicineCircleColor
import com.aritra.medsync.ui.theme.OnPrimaryContainer
import com.aritra.medsync.ui.theme.OnSurface60
import com.aritra.medsync.ui.theme.medium14
import com.aritra.medsync.ui.theme.medium16
import com.aritra.medsync.ui.theme.normal14
import com.aritra.medsync.utils.Utils
import com.aritra.medsync.utils.Utils.getMedicineImage
import com.aritra.medsync.utils.hasPassed
import com.aritra.medsync.utils.toFormattedTimeString

@Composable
fun MedicationHistoryCard(
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

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MedicineCircleColor)
                        .padding(12.dp),
                    painter = getMedicineImage(medication.medicineType),
                    contentDescription = null
                )

                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = medication.medicineName,
                        style = medium16.copy(color = OnPrimaryContainer)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "${medication.pillsAmount} ${Utils.getMedicineUnit(medication.medicineType)} | ${medication.pillsFrequency}",
                        style = normal14.copy(color = OnSurface60)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    val medicationStatusText = when {
                        medication.reminderTime.hasPassed() -> {
                            if (medication.isTaken) {
                                stringResource(
                                    id = R.string.medication_taken_at,
                                    medication.reminderTime.toFormattedTimeString()
                                )
                            } else {
                                stringResource(
                                    id = R.string.medication_skipped_at,
                                    medication.reminderTime.toFormattedTimeString()
                                )
                            }
                        }

                        else -> stringResource(
                            id = R.string.medication_scheduled_at,
                            medication.reminderTime.toFormattedTimeString()
                        )
                    }

                    Text(
                        text = medicationStatusText,
                        style = medium14.copy(OnSurface60),
                        modifier = Modifier
                            .border(1.dp, OnSurface60, CircleShape)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}
