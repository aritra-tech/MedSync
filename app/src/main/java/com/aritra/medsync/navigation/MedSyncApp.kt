package com.aritra.medsync.navigation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aritra.medsync.R
import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.ui.screens.account.AccountScreen
import com.aritra.medsync.ui.screens.account.AccountViewModel
import com.aritra.medsync.ui.screens.addMedication.AddMedication
import com.aritra.medsync.ui.screens.addMedication.MedicationConfirmationScreen
import com.aritra.medsync.ui.screens.addMedication.viewModel.AddMedicationViewModel
import com.aritra.medsync.ui.screens.addMedication.viewModel.MedicationConfirmViewModel
import com.aritra.medsync.ui.screens.appointment.AddAppointmentScreen
import com.aritra.medsync.ui.screens.appointment.AppointmentDetailScreen
import com.aritra.medsync.ui.screens.appointment.AppointmentScreen
import com.aritra.medsync.ui.screens.appointment.viewModel.AppointmentViewModel
import com.aritra.medsync.ui.screens.history.HistoryScreen
import com.aritra.medsync.ui.screens.history.viewmodel.HistoryViewModel
import com.aritra.medsync.ui.screens.homeScreen.HomeScreen
import com.aritra.medsync.ui.screens.homeScreen.viewmodel.HomeViewModel
import com.aritra.medsync.ui.screens.intro.GetStartedScreen
import com.aritra.medsync.ui.screens.intro.GoogleAuthUiClient
import com.aritra.medsync.ui.screens.intro.SigninViewModel
import com.aritra.medsync.ui.screens.intro.SplashScreen
import com.aritra.medsync.ui.screens.prescription.PrescriptionScreen
import com.aritra.medsync.ui.theme.Background
import com.aritra.medsync.ui.theme.DMSansFontFamily
import com.aritra.medsync.ui.theme.OnPrimaryContainer
import com.aritra.medsync.ui.theme.OnSurface40
import com.aritra.medsync.ui.theme.PrimaryContainer
import com.aritra.medsync.utils.BackPressHandler

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MedSyncApp(googleAuthUiClient: GoogleAuthUiClient) {


    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()

    val screensWithoutNavigationBar = listOf(
        MedSyncScreens.Splash.name,
        MedSyncScreens.GetStarted.name,
        MedSyncScreens.AddMedication.name,
        MedSyncScreens.MedicationConfirmScreen.name,
        MedSyncScreens.ProfileScreen.name,
        MedSyncScreens.PrescriptionScreen.name,
        MedSyncScreens.AddAppointmentScreen.name,
        "${MedSyncScreens.AppointmentDetailsScreen.name}/{appointmentId}"
    )

    BackPressHandler()

    Scaffold(
        bottomBar = {
            ShowBottomNavigation(
                backStackEntry,
                screensWithoutNavigationBar,
                navController
            )
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) {


        val viewModel: AddMedicationViewModel = hiltViewModel()
        val signInViewModel: SigninViewModel = hiltViewModel()
        val medicationConfirmViewModel: MedicationConfirmViewModel = hiltViewModel()
        val homeViewModel: HomeViewModel = hiltViewModel()
        val historyViewModel: HistoryViewModel = hiltViewModel()
        val appointmentViewModel: AppointmentViewModel = hiltViewModel()

        NavHost(
            navController = navController,
            startDestination = MedSyncScreens.Splash.name
        ) {

            composable(MedSyncScreens.Splash.name) {
                SplashScreen(navController = navController, googleAuthUiClient)
            }

            composable(MedSyncScreens.GetStarted.name) {
                val state by signInViewModel.state.collectAsStateWithLifecycle()

                GetStartedScreen(
                    navController,
                    state = state,
                    googleAuthUiClient,
                    signInViewModel
                )
            }

            composable(MedSyncScreens.Home.name) {
                HomeScreen(
                    userData = googleAuthUiClient.getSignedInUser(),
                    onFabClicked = { navController.navigate(MedSyncScreens.AddMedication.name) },
                    navigateToUpdateScreen = { medicineID ->
                        navController.navigate("${MedSyncScreens.UpdateMedication.name}/$medicineID")
                    },
                    homeViewModel
                )
            }

            composable(MedSyncScreens.AddMedication.name) {
                AddMedication(
                    navController,
                    goToMedicationConfirmScreen = {
                        val bundle = Bundle()
                        bundle.putParcelableArrayList("medication", ArrayList(it))
                        navController.currentBackStackEntry?.savedStateHandle.apply {
                            this?.set("medication", bundle)
                        }
                        navController.navigate(MedSyncScreens.MedicationConfirmScreen.name)
                    },
                    viewModel
                )
            }

            composable(MedSyncScreens.MedicationConfirmScreen.name) {
                val result =
                    navController.previousBackStackEntry?.savedStateHandle?.get<Bundle>("medication")
                val medication = result?.getParcelableArrayList<Medication>("medication")
                MedicationConfirmationScreen(
                    medication,
                    navController,
                    medicationConfirmViewModel
                )
            }

            composable(MedSyncScreens.History.name) {
                HistoryScreen(historyViewModel)
            }

            composable(MedSyncScreens.Account.name) {
                AccountScreen(
                    navController = navController,
                    userData = googleAuthUiClient.getSignedInUser()
                )
            }

            composable(MedSyncScreens.PrescriptionScreen.name) {
                PrescriptionScreen()
            }

            composable(MedSyncScreens.AppointmentScreen.name) {
                AppointmentScreen(
                    onFabClicked = { navController.navigate(MedSyncScreens.AddAppointmentScreen.name) },
                    onAppointmentClicked = { appointmentId ->
                        navController.navigate("${MedSyncScreens.AppointmentDetailsScreen.name}/$appointmentId")
                    },
                    appointmentViewModel = appointmentViewModel
                )
            }

            composable(MedSyncScreens.AddAppointmentScreen.name) {
                AddAppointmentScreen(navController, appointmentViewModel)
            }

            composable(
                route = "${MedSyncScreens.AppointmentDetailsScreen.name}/{appointmentId}",
                arguments = listOf(navArgument("appointmentId") { type = NavType.StringType })
            ) { backStackEntry ->
                val appointmentId = backStackEntry.arguments?.getString("appointmentId") ?: ""
                AppointmentDetailScreen(
                    navController = navController,
                    appointmentViewModel = appointmentViewModel,
                    appointmentId = appointmentId
                )
            }
        }
    }

}

@Composable
fun ShowBottomNavigation(
    backStackEntry: State<NavBackStackEntry?>,
    screensWithoutNavigationBar: List<String>,
    navController: NavHostController
) {
    if (backStackEntry.value?.destination?.route !in screensWithoutNavigationBar) {
        NavigationBar(
            containerColor = Background
        ) {
            val bottomNavItems = listOf(
                BottomNavItem(
                    name = "Medication",
                    route = MedSyncScreens.Home.name,
                    icon = painterResource(R.drawable.medication_icon)

                ),
                BottomNavItem(
                    name = "Appointment",
                    route = MedSyncScreens.AppointmentScreen.name,
                    icon = painterResource(R.drawable.appointment_icon)
                ),
                BottomNavItem(
                    name = "History",
                    route = MedSyncScreens.History.name,
                    icon = painterResource(R.drawable.history_icon)
                ),
                BottomNavItem(
                    name = "Account",
                    route = MedSyncScreens.Account.name,
                    icon = painterResource(R.drawable.account_icon)
                )
            )
            bottomNavItems.forEach { item ->
                NavigationBarItem(
                    alwaysShowLabel = true,
                    icon = {
                        Icon(
                            painter = item.icon,
                            contentDescription = item.name,
                            tint = if (backStackEntry.value?.destination?.route == item.route)
                                OnPrimaryContainer
                            else
                                OnSurface40
                        )
                    },
                    label = {
                        Text(
                            text = item.name,
                            fontFamily = DMSansFontFamily,
                            color = if (backStackEntry.value?.destination?.route == item.route)
                                OnPrimaryContainer
                            else
                                OnSurface40,
                            fontWeight = if (backStackEntry.value?.destination?.route == item.route)
                                FontWeight.SemiBold
                            else
                                FontWeight.Normal,
                        )
                    },
                    selected = backStackEntry.value?.destination?.route == item.route,
                    onClick = {
                        val currentDestination =
                            navController.currentBackStackEntry?.destination?.route
                        if (item.route != currentDestination) {
                            navController.navigate(item.route) {
                                navController.graph.findStartDestination().let { route ->
                                    popUpTo(route.id) {
                                        saveState = true
                                    }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = PrimaryContainer
                    )
                )
            }
        }
    }
}
