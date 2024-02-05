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
import androidx.compose.ui.unit.dp
import com.aritra.medsync.R
import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.ui.theme.Green
import com.aritra.medsync.ui.theme.OnPrimaryContainer
import com.aritra.medsync.ui.theme.bold24
import com.aritra.medsync.ui.theme.lightBlue
import com.aritra.medsync.ui.theme.normal14
import timber.log.Timber

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

    Card(
        modifier = Modifier
            .padding(8.dp)
            .height(150.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            lightBlue
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

                medicationTaken = medication.filter { it.isTaken }.size
                totalNumberOfMedication = medication.size
                medicationRemaining = totalNumberOfMedication - medicationTaken
                medicationPercentage = (medicationTaken.toFloat() / totalNumberOfMedication.toFloat()) * 100
                Timber.d("Percentage: $medicationPercentage")

                Text(
                    text = stringResource(R.string.your_plan_for_today),
                    style = bold24,
                    color = OnPrimaryContainer
                )
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = stringResource(R.string.medication_s_taken, medicationTaken),
                    style = normal14,
                    color = OnPrimaryContainer
                )

                Spacer(modifier = Modifier.height(10.dp))


                Text(
                    text = stringResource(R.string.medication_s_remaining, medicationRemaining),
                    style = normal14,
                    color = OnPrimaryContainer
                )
            }

            Box(
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.then(
                        Modifier
                            .size(110.dp)
                            .padding(end = 18.dp)
                            .padding(vertical = 30.dp)
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
            }
        }
    }
}