package com.aritra.medsync.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aritra.medsync.R
import com.aritra.medsync.components.ProfileContainer
import com.aritra.medsync.components.SettingsItem
import com.aritra.medsync.domain.model.UserData
import com.aritra.medsync.ui.theme.OnSurface20
import com.aritra.medsync.ui.theme.PrimarySurface
import com.aritra.medsync.ui.theme.medium32
import com.aritra.medsync.ui.theme.normal14
import com.aritra.medsync.utils.Utils.inviteFriends
import com.aritra.medsync.utils.Utils.mailTo

@Composable
fun SettingsScreen(
    userData: UserData?,
    settingsViewModel: SettingsViewModel
) {
    val context = LocalContext.current
    val themeStateObserver by settingsViewModel.themeState.collectAsState()

    /***********************  UI Content  ***********************/

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimarySurface)
            .padding(horizontal = 16.dp)
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 42.dp)
                    .statusBarsPadding(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ProfileContainer(userData)

                    Spacer(modifier = Modifier.height(16.dp))

                    if (userData?.username != null) {
                        Text(
                            text = userData.username,
                            color = OnSurface20,
                            style = medium32
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            Column {
//                Text(
//                    modifier = Modifier.fillMaxWidth(),
//                    text = stringResource(R.string.preference),
//                    color = OnPrimaryContainer,
//                    style = bold18
//                )
//
//                SettingsSwitch(
//                    iconId = R.drawable.dark_mode,
//                    itemName = stringResource(R.string.dark_theme),
//                    isChecked = themeStateObserver.isDarkMode,
//                    onCheckedChange = {
//                        settingsViewModel.toggleTheme()
//                    }
//                )

//                Text(
//                    modifier = Modifier.fillMaxWidth(),
//                    text = "More",
//                    color = OnPrimaryContainer,
//                    style = bold18
//                )

                SettingsItem(
                    onClick = { mailTo(context) },
                    iconId = R.drawable.send,
                    itemName = stringResource(R.string.send_feedback)
                )

                SettingsItem(
                    onClick = { inviteFriends(context) },
                    iconId = R.drawable.smile,
                    itemName = stringResource(R.string.share_medsync_to_friends)
                )

                SettingsItem(
                    onClick = { /*TODO*/ },
                    iconId = R.drawable.rate,
                    itemName = stringResource(R.string.rate_medsync)
                )
            }
        }

        item {

            Column(
                modifier = Modifier.fillMaxWidth().padding(top = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .alpha(0.25f),
                    text = "v.1.0.0",
                    color = OnSurface20,
                    style = normal14
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = stringResource(R.string.made_with_in_india),
                    style = normal14.copy(OnSurface20)
                )
            }
        }
    }
}