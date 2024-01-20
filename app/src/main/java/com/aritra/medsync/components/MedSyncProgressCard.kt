package com.aritra.medsync.components

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.aritra.medsync.ui.theme.bold24
import com.aritra.medsync.ui.theme.normal14

@Composable
fun MedSyncProgressCard(medication: List<Medication>) {

    val medicationTaken = medication.filter { it.isTaken }.size
    val totalNumberOfMedication = medication.size
    val medicationRemaining = totalNumberOfMedication - medicationTaken
    val medicationPercentage = (medicationTaken.toFloat() / totalNumberOfMedication.toFloat()) * 100

    Card(
        modifier = Modifier
            .padding(8.dp)
            .height(150.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp, 24.dp, 0.dp, 16.dp),
            ) {

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
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.then(
                        Modifier
                            .size(100.dp)
                            .padding(end = 16.dp)
                            .padding(vertical = 35.dp)
                    ),
                    progress = animateFloatAsState(
                        targetValue = medicationPercentage / 100,
                        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
                        label = "SOC progress"
                    ).value,
                    color = Green,
                    strokeWidth = 12.dp,
                    strokeCap = StrokeCap.Round,
                    trackColor = Color.White
                )

                // TODO : Need to fix the text
//                Text(
//                    text = medicationPercentage.toString(),
//                    style = medium16,
//                    color = Color.White
//                )
            }
        }
    }
}