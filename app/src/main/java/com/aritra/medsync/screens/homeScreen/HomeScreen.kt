package com.aritra.medsync.screens.homeScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aritra.medsync.R
import com.aritra.medsync.ui.theme.bold20
import com.aritra.medsync.ui.theme.bold24
import com.aritra.medsync.ui.theme.medium16
import com.aritra.medsync.ui.theme.medium18
import com.aritra.medsync.ui.theme.normal12
import java.time.LocalTime
import java.time.ZoneId


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    onFabClicked: () -> Unit,
    navigateToUpdateScreen: (medicineID: Int) -> Unit
) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(vertical = 90.dp),
                onClick = { onFabClicked() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Add Meds"
                )
            }
        },
    ) {
        Surface(modifier = Modifier.padding(it)) {

            Column(modifier = Modifier.fillMaxSize()) {
                Greetings()
                OverviewCard()

                NoMedication()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Greetings() {

    val currentHour = LocalTime.now(ZoneId.systemDefault()).hour

    val greetingText = when (currentHour) {
        in 5..11 -> "Good morning,"
        in 12..16 -> "Good afternoon,"
        in 17..20 -> "Good evening,"
        else -> "Good night,"
    }

    Column {
        Text(
            modifier = Modifier.padding(top = 10.dp, start = 16.dp),
            text = greetingText,
            style = bold24
        )
        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = "Aritra!",
            style = bold20
        )
    }
}

@Composable
fun OverviewCard() {

    Card(
        modifier = Modifier
            .padding(16.dp)
            .height(220.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {

        Row(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(24.dp, 24.dp, 0.dp, 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Text(
                    text = "Your plan for today",
                    fontWeight = FontWeight.Medium,
                    style = medium16,
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "1 medicine done",
                    style = normal12,
                )
                Text(
                    text = "3 medicine in progress",
                    style = normal12,
                )
                // TODO: Add a progress bar to track the medications taken and left
            }

            // TODO: Will be replacing with the users image or if the user is female then it will show female doctor else male
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Image(
                    painter = painterResource(id = R.drawable.female_doctor),
                    contentDescription = "Doctor"
                )
            }
        }
    }
}

@Composable
fun NoMedication() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, start = 16.dp)
    ) {
        Text(
            text = "Add your meds",
            style = medium18,
        )
    }
}

