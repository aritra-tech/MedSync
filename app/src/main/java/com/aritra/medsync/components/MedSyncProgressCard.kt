package com.aritra.medsync.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aritra.medsync.R
import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.ui.theme.Green
import com.aritra.medsync.ui.theme.OnSurface40
import com.aritra.medsync.ui.theme.OnSurface60
import com.aritra.medsync.ui.theme.bold24
import com.aritra.medsync.ui.theme.normal14

@Composable
fun MedSyncProgressCard(medication: List<Medication>) {

    var medicationTaken by rememberSaveable {
        mutableIntStateOf(0)
    }
    var totalNumberOfMedication by rememberSaveable {
        mutableIntStateOf(0)
    }
    var medicationRemaining by rememberSaveable {
        mutableIntStateOf(0)
    }
    var medicationPercentage by rememberSaveable {
        mutableFloatStateOf(0f)
    }

    Box(
        modifier = Modifier
            .height(150.dp)
            .border(
                border = BorderStroke(1.dp, OnSurface40.copy(alpha = 0.2f)),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {

                medicationTaken = medication.filter { it.isTaken }.size
                totalNumberOfMedication = medication.size
                medicationRemaining = totalNumberOfMedication - medicationTaken
                medicationPercentage = (medicationTaken.toFloat() / totalNumberOfMedication.toFloat()) * 100

                Text(
                    text = stringResource(R.string.your_plan_for_today),
                    fontWeight = FontWeight.Medium,
                    style = bold24,
                )
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "$medicationTaken medication's taken",
                    style = normal14,
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "$medicationRemaining medication's remaining",
                    style = normal14,
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(100.dp),
                    progress = animateFloatAsState(
                        targetValue = medicationPercentage / 100,
                        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
                        label = "Medication progress"
                    ).value,
                    color = Green,
                    strokeWidth = 12.dp,
                    strokeCap = StrokeCap.Round,
                    trackColor = Color.White
                )

                Text(
                    text = "${medicationPercentage.toInt()}%",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }
        }
    }
}