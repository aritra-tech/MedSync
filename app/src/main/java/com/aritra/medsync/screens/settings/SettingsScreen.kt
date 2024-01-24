package com.aritra.medsync.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.aritra.medsync.R
import com.aritra.medsync.components.SettingsItem
import com.aritra.medsync.ui.theme.OnPrimaryContainer
import com.aritra.medsync.ui.theme.OnSurface20
import com.aritra.medsync.ui.theme.medium16
import com.aritra.medsync.ui.theme.medium32

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
                // TODO: ON CLICK WILL GO TO PROFILE SCREEN
                Image(
                    painter = painterResource(id = R.drawable.no_user_profile_picture),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )

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
                onClick = { /*TODO*/ },
                iconId = R.drawable.capsule,
                itemName = "Add a prescription"
            )

            SettingsItem(
                onClick = { /*TODO*/ },
                iconId = R.drawable.capsule,
                itemName = "Add a appointment"
            )

            Text(
                modifier = Modifier.padding(16.dp),
                text = "Preference",
                color = OnPrimaryContainer,
                style = medium16
            )

            SettingsItem(
                onClick = { /*TODO*/ },
                iconId = R.drawable.capsule,
                itemName = "Lock my screen"
            )

            SettingsItem(
                onClick = { /*TODO*/ },
                iconId = R.drawable.capsule,
                itemName = "Dark theme"
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
                itemName = "Send feedback"
            )

            SettingsItem(
                onClick = { /*TODO*/ },
                iconId = R.drawable.capsule,
                itemName = "Share MedSync to friends"
            )

            SettingsItem(
                onClick = { /*TODO*/ },
                iconId = R.drawable.capsule,
                itemName = "Rate MedSync"
            )

            SettingsItem(
                onClick = { /*TODO*/ },
                iconId = R.drawable.capsule,
                itemName = "About"
            )
        }
    }
}