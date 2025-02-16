package com.aritra.medsync.ui.screens.intro

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aritra.medsync.R
import com.aritra.medsync.navigation.MedSyncScreens
import com.aritra.medsync.ui.theme.bold30
import kotlinx.coroutines.delay

private const val ANIMATION_DURATION = 1000
private const val SPLASH_DELAY = 1000L

@Composable
fun SplashScreen(navController: NavController, googleAuthUiClient: GoogleAuthUiClient) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnimation = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = ANIMATION_DURATION),
        label = "alphaAnimation"
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(SPLASH_DELAY)
        navigateToNextScreen(navController, googleAuthUiClient)
    }

    SplashContent(alphaAnimation.value)
}

@Composable
private fun SplashContent(alpha: Float) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedLogo(alpha)
            AnimatedText(alpha)
        }
    }
}

@Composable
private fun AnimatedLogo(alpha: Float) {
    Image(
        modifier = Modifier
            .alpha(alpha)
            .size(240.dp),
        painter = painterResource(id = R.drawable.medsync_logo),
        contentDescription = "MedSync Logo",
    )
}

@Composable
private fun AnimatedText(alpha: Float) {
    Text(
        modifier = Modifier.alpha(alpha),
        text = "MedSync",
        style = bold30,
        color = Color.Black
    )
}

private fun navigateToNextScreen(navController: NavController, googleAuthUiClient: GoogleAuthUiClient) {
    navController.popBackStack()
    val route = if (googleAuthUiClient.getSignedInUser() != null) {
        MedSyncScreens.Home.name
    } else {
        MedSyncScreens.GetStarted.name
    }
    navController.navigate(route)
}