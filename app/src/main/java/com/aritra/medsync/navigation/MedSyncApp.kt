package com.aritra.medsync.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aritra.medsync.screens.AddMedication
import com.aritra.medsync.screens.AddMedicationViewModel
import com.aritra.medsync.screens.HomeScreen
import com.aritra.medsync.screens.MedicationConfirmationScreen
import com.aritra.medsync.screens.SplashScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MedSyncApp() {

    val navController = rememberNavController()
    val viewModel: AddMedicationViewModel = hiltViewModel()

    NavHost(navController = navController,
        startDestination = MedSyncScreens.Splash.name,
        enterTransition = {
            fadeIn(animationSpec = tween(220, 90)) +
                    scaleIn(initialScale = 0.92f, animationSpec = tween(220, 90))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(90))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(220, 90)) + scaleIn(
                initialScale = 0.92f,
                animationSpec = tween(220, 90)
            )
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(90))
        }
    ) {
        composable(MedSyncScreens.Splash.name) {
            SplashScreen(navController = navController)
        }
        composable(MedSyncScreens.Home.name) {
            HomeScreen(
                onFabClicked = { navController.navigate(MedSyncScreens.AddMedication.name) },
                navigateToUpdateScreen = { medicineID ->
                    navController.navigate("${MedSyncScreens.UpdateMedication.name}/$medicineID")
                }
            )
        }
        composable(MedSyncScreens.AddMedication.name) {
            AddMedication(navController,viewModel)
        }
        composable(MedSyncScreens.MedicationConfirmScreen.name) {
            MedicationConfirmationScreen(navController)
        }
    }

}