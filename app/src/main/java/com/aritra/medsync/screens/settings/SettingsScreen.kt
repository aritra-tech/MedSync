package com.aritra.medsync.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.aritra.medsync.R
import com.aritra.medsync.components.ProfileContainer
import com.aritra.medsync.components.SettingsItem
import com.aritra.medsync.components.SettingsSwitch
import com.aritra.medsync.ui.theme.OnPrimaryContainer
import com.aritra.medsync.ui.theme.OnSurface20
import com.aritra.medsync.ui.theme.medium16
import com.aritra.medsync.ui.theme.medium32
import com.aritra.medsync.utils.Constants.APPOINTMENT_SCREEN
import com.aritra.medsync.utils.Constants.PRESCRIPTION_SCREEN
import com.aritra.medsync.utils.Constants.PROFILE_SCREEN

@Composable
fun SettingsScreen(
    navController: NavHostController
) {


    /***********************  UI Content  ***********************/
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(52.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileContainer {
                    navController.navigate(PROFILE_SCREEN)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Aritra",
                    color = OnSurface20,
                    style = medium32
                )
            }
        }

        Column {
            Text(
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp),
                text = "General",
                color = OnPrimaryContainer,
                style = medium16
            )

            SettingsItem(
                onClick = { navController.navigate(PRESCRIPTION_SCREEN) },
                iconId = R.drawable.capsule,
                itemName = stringResource(R.string.add_a_prescription)
            )

            SettingsItem(
                onClick = { navController.navigate(APPOINTMENT_SCREEN) },
                iconId = R.drawable.capsule,
                itemName = stringResource(R.string.add_a_appointment)
            )

            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.preference),
                color = OnPrimaryContainer,
                style = medium16
            )

            SettingsItem(
                onClick = { /*TODO*/ },
                iconId = R.drawable.capsule,
                itemName = stringResource(R.string.lock_my_screen)
            )

            SettingsSwitch(
                iconId = R.drawable.capsule,
                itemName = stringResource(R.string.dark_theme),
                isChecked = true,
                onCheckedChange = {}
            )

            Text(
                modifier = Modifier.padding(16.dp),
                text = "More",
                color = OnPrimaryContainer,
                style = medium16
            )

            SettingsItem(
                onClick = { /*TODO*/ },
                iconId = R.drawable.capsule,
                itemName = stringResource(R.string.send_feedback)
            )

            SettingsItem(
                onClick = { /*TODO*/ },
                iconId = R.drawable.capsule,
                itemName = stringResource(R.string.share_medsync_to_friends)
            )

            SettingsItem(
                onClick = { /*TODO*/ },
                iconId = R.drawable.capsule,
                itemName = stringResource(R.string.rate_medsync)
            )

            SettingsItem(
                onClick = { /*TODO*/ },
                iconId = R.drawable.capsule,
                itemName = stringResource(R.string.about)
            )
        }
    }
}