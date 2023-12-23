package com.aritra.medsync.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.aritra.medsync.R
import com.aritra.medsync.components.CustomTopAppBar
import com.aritra.medsync.components.MedSyncButton
import com.aritra.medsync.navigation.MedSyncScreens
import com.aritra.medsync.ui.theme.OnSurface20
import com.aritra.medsync.ui.theme.backgroundColor
import com.aritra.medsync.ui.theme.medium16

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationConfirmationScreen(navController: NavHostController) {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.completed_lottie_animation)
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopAppBar(
                title = "",
                colors = TopAppBarDefaults.topAppBarColors(backgroundColor)
            ) {
                navController.popBackStack()
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(backgroundColor)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                modifier = Modifier
                    .padding(20.dp, 30.dp)
                    .size(180.dp),
                composition = composition,
                iterations = 1
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "We will make sure that you have taken the medicine",
                style = medium16,
                color = OnSurface20,
                textAlign = TextAlign.Center
            )

            // TODO : Need to add some prescription type ui where the details of the medicine will be written down

            Spacer(modifier = Modifier.weight(1f))

            MedSyncButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Done"
            ) {
                navController.navigate(route = MedSyncScreens.Home.name)
            }
        }
    }
}

@Preview
@Composable
fun ConfirmationScreenPrev() {
    MedicationConfirmationScreen(navController = rememberNavController())
}