package com.aritra.medsync.components

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.aritra.medsync.R
import com.aritra.medsync.ui.theme.OnSurface20
import com.aritra.medsync.ui.theme.OnSurface60
import com.aritra.medsync.ui.theme.medium22
import com.aritra.medsync.ui.theme.normal16

@Composable
fun MedSyncEmptyState(
    stateTitle: String,
    stateDescription: String?,
    @RawRes animationId: Int = R.raw.empty_box_animation
) {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(animationId)
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        LottieAnimation(
            modifier = Modifier.size(240.dp),
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stateTitle,
            textAlign = TextAlign.Center,
            style = medium22,
            color = OnSurface20
        )

        stateDescription?.let {
            Text(
                text = it,
                textAlign = TextAlign.Center,
                style = normal16,
                color = OnSurface60
            )
        }
    }
}