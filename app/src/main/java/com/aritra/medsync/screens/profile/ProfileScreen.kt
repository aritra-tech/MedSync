package com.aritra.medsync.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.aritra.medsync.components.MedSyncButton
import com.aritra.medsync.components.MedSyncReminderTextField
import com.aritra.medsync.components.MedSyncTextField
import com.aritra.medsync.components.MedSyncTopAppBar
import com.aritra.medsync.components.ProfileContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController
) {

    Scaffold(
        topBar = {
            MedSyncTopAppBar(
                title = "Profile",
                colors = TopAppBarDefaults.topAppBarColors(Color.White),
                onBackPress = {
                    navController.popBackStack()
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(bottom = 20.dp)
                .imePadding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileContainer {

            }

            Spacer(modifier = Modifier.height(20.dp))

            MedSyncTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 0.dp),
                headerText = "Name",
                hintText = "John Doe",
                value = "",
                onValueChange = {}
            )

            Spacer(modifier = Modifier.height(20.dp))

            MedSyncTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 0.dp),
                headerText = "Age",
                hintText = "30",
                value = "",
                onValueChange = {}
            )

            Spacer(modifier = Modifier.weight(1f))

            MedSyncButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                onClick = {
                    navController.popBackStack()
                },
                text = "Save"
            )
        }
    }
}