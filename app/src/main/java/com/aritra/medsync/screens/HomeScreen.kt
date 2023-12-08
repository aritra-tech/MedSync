package com.aritra.medsync.screens

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


@Composable
fun HomeScreen(
    onFabClicked: () -> Unit,
    navigateToUpdateScreen: (medicineID: Int) -> Unit
) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
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

@Composable
fun Greetings() {
    Column {
        Text(
            modifier = Modifier.padding(top = 10.dp, start = 16.dp),
            text = "Good morning,",
            style = MaterialTheme.typography.displaySmall
        )
        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = "Aritra!",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.displaySmall
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
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "1 medicine done",
                    style = MaterialTheme.typography.titleSmall,
                )
                Text(
                    text = "3 medicine in progress",
                    style = MaterialTheme.typography.titleSmall,
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
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}

