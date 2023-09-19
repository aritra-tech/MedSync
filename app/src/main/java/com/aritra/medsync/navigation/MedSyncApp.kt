package com.aritra.medsync.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aritra.medsync.LogInScreen
import com.aritra.medsync.SplashScreen

@Composable
fun MedSyncApp() {

    val navController = rememberNavController()
    
    NavHost(navController = navController,
        startDestination = MedSyncScreens.Splash.name
    ) {
        composable(MedSyncScreens.Splash.name) {
            SplashScreen(navController = navController)
        }
        composable(MedSyncScreens.Login.name) {
            LogInScreen(navController = navController)
        }
    }

}