package com.aritra.medsync.components

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.ui.screens.homeScreen.viewmodel.MedicationDetailsViewModel
import com.aritra.medsync.ui.theme.Green
import com.aritra.medsync.ui.theme.MedicineCircleColor
import com.aritra.medsync.ui.theme.OnPrimaryContainer
import com.aritra.medsync.ui.theme.OnSurface60
import com.aritra.medsync.ui.theme.bold24
import com.aritra.medsync.ui.theme.dividerColor
import com.aritra.medsync.ui.theme.lightGreen
import com.aritra.medsync.ui.theme.medium16
import com.aritra.medsync.ui.theme.medium18
import com.aritra.medsync.ui.theme.normal14
import com.aritra.medsync.utils.Utils.getMedicineImage
import com.aritra.medsync.utils.Utils.getMedicineUnit
import com.aritra.medsync.utils.hasPassed
import com.aritra.medsync.utils.onClick
import com.aritra.medsync.utils.toFormattedTimeString

@Composable
fun MedicationCard(
    medication: Medication
) {

    val updateMedicationViewModel: MedicationDetailsViewModel = hiltViewModel()
    var isTakenClicked by remember {
        mutableStateOf(medication.isTaken)
    }
    var isSkippedClicked by remember {
        mutableStateOf(medication.isTaken.not())
    }
    var isTakenConfirmed by remember { mutableStateOf(false) }
    val context = LocalContext.current

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
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = medication.reminderTime.toFormattedTimeString(),
                    style = bold24.copy(color = OnPrimaryContainer)
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = medication.pillsFrequency,
                    style = normal14.copy(color = OnSurface60)
                )
            }

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
                    if (isTakenClicked) {
                        Text(
                            text = medication.medicineName,
                            style = medium16.copy(color = OnPrimaryContainer, textDecoration = TextDecoration.LineThrough)
                        )
                    } else {
                        Text(
                            text = medication.medicineName,
                            style = medium18.copy(color = OnPrimaryContainer)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))



                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "${medication.pillsAmount} ${getMedicineUnit(medication.medicineType)}",
                            style = normal14.copy(color = OnSurface60)
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        AnimatedVisibility(
                            visible = medication.reminderTime.hasPassed() && !isTakenConfirmed,
                            enter = fadeIn(animationSpec = tween(200))
                        ) {
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(100.dp))
                                    .background(lightGreen)
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,

                            ) {
                                Icon(
                                    modifier = Modifier
                                        .size(25.dp)
                                        .onClick {
                                            isTakenClicked = isTakenClicked.not()
                                            if (isTakenClicked) {
                                                isSkippedClicked = false
                                                isTakenConfirmed = true
                                            }
                                            updateMedicationViewModel.isMedicationTaken(
                                                medication,
                                                isTakenClicked
                                            )
                                            Toast
                                                .makeText(context, "Medication taken", Toast.LENGTH_SHORT)
                                                .show()
                                        },
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Medication Taken",
                                    tint = Green,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

