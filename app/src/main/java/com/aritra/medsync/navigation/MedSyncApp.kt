package com.aritra.medsync.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aritra.medsync.screens.HomeScreen
import com.aritra.medsync.screens.SplashScreen

@Composable
fun MedSyncApp() {

    val navController = rememberNavController()
    
    NavHost(navController = navController,
        startDestination = MedSyncScreens.Splash.name
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
    }

}