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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aritra.medsync.R
import com.aritra.medsync.components.ProfileContainer
import com.aritra.medsync.components.SettingsItem
import com.aritra.medsync.domain.model.UserData
import com.aritra.medsync.navigation.MedSyncScreens
import com.aritra.medsync.ui.theme.OnSurface20
import com.aritra.medsync.ui.theme.PrimarySurface
import com.aritra.medsync.ui.theme.medium32
import com.aritra.medsync.ui.theme.normal14
import com.aritra.medsync.utils.Utils.inviteFriends
import com.aritra.medsync.utils.Utils.mailTo
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

@Composable
fun SettingsScreen(
    navController: NavController,
    userData: UserData?,
    settingsViewModel: SettingsViewModel
) {
    val context = LocalContext.current
    var showLogoutDialog by remember { mutableStateOf(false) }

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()
    val googleSignInClient = GoogleSignIn.getClient(context, gso)

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text(text = "Logout Confirmation") },
            text = { Text(text = "Are you sure you want to logout from MedSync?") },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false
                        googleSignInClient.signOut().addOnCompleteListener {
                            navController.navigate(MedSyncScreens.GetStarted.name) {
                                popUpTo(0) {
                                    inclusive = true
                                }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red.copy(alpha = 0.8f)
                    )
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showLogoutDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    /***********************  UI Content  ***********************/

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimarySurface)
            .padding(horizontal = 16.dp)
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top section with profile
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 42.dp)
                    .statusBarsPadding(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
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

        // Middle section with settings items
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
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

        Spacer(Modifier.weight(1f))

        Column(
            modifier = Modifier.fillMaxWidth().padding(bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logout Button
            OutlinedButton(
                onClick = { showLogoutDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red.copy(alpha = 0.6f)
                )
            ) {
                Text(
                    text = "Logout",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Version Info
            Text(
                modifier = Modifier.alpha(0.25f),
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