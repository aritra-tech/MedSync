package com.aritra.medsync.screens.medicationConfirmation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.aritra.medsync.R
import com.aritra.medsync.components.MedSyncButton
import com.aritra.medsync.components.MedSyncTopAppBar
import com.aritra.medsync.components.MedicationDetailsInvoice
import com.aritra.medsync.components.TicketView
import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.domain.model.MedicationConfirmation
import com.aritra.medsync.navigation.MedSyncScreens
import com.aritra.medsync.ui.theme.OnPrimaryContainer
import com.aritra.medsync.ui.theme.OnSurface20
import com.aritra.medsync.ui.theme.backgroundColor
import com.aritra.medsync.ui.theme.bold24
import com.aritra.medsync.ui.theme.medium16
import com.aritra.medsync.utils.Utils.getMedicationItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationConfirmationScreen(
    medication: List<Medication>?,
    navController: NavHostController,
    medicationConfirmViewModel: MedicationConfirmViewModel
) {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.completed_lottie_animation)
    )
    val medicationResult = medication?.first()

    val medicationItems = medicationResult?.getMedicationItem()

    LaunchedEffect(Unit) {
        medicationConfirmViewModel.medicationSaved.collect {
            navController.navigate(route = MedSyncScreens.Home.name)
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MedSyncTopAppBar(
                title = "",
                colors = TopAppBarDefaults.topAppBarColors(backgroundColor)
            ) {
                navController.popBackStack()
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(backgroundColor)
                .padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                modifier = Modifier
                    .padding(20.dp, 30.dp)
                    .size(180.dp),
                composition = composition,
                iterations = LottieConstants.IterateForever
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.just_relax),
                style = bold24,
                color = OnPrimaryContainer
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = stringResource(R.string.no_need_to_worry_we_are_here_to_remind_you_about_your_medicines),
                style = medium16,
                color = OnSurface20,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            TicketView {
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 25.dp, vertical = 30.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (medicationItems != null) {
                        items(medicationItems.size) { index ->
                            val invoice = medicationItems[index]
                            MedicationDetailsInvoice(
                                attribute = invoice.first,
                                value = invoice.second
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            MedSyncButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Done"
            ) {
                medication?.let {
                    MedicationConfirmation(
                        it
                    )
                }?.let {
                    medicationConfirmViewModel.saveMedication(
                        MedicationConfirmation(
                            medication
                        )
                    )
                }
                navController.navigate(route = MedSyncScreens.Home.name)
            }
        }
    }
}
