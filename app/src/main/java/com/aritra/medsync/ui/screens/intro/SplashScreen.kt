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

@Composable
fun SplashScreen(navController: NavController, googleAuthUiClient: GoogleAuthUiClient) {
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnimation = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000
        ), label = ""
    )
    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(1000L)
        navController.popBackStack()
    }

    LaunchedEffect(key1 = Unit) {
        if(googleAuthUiClient.getSignedInUser() != null) {
            navController.navigate(MedSyncScreens.Home.name)
        } else {
            navController.navigate(route = MedSyncScreens.GetStarted.name)
        }
    }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .alpha(alphaAnimation.value)
                    .size(240.dp),
                painter = painterResource(id = R.drawable.medsync_logo),
                contentDescription = "null",
            )

            Text(
                modifier = Modifier.alpha(alphaAnimation.value),
                text = "MedSync",
                style = bold30,
                color = Color.Black
            )
        }
    }
}