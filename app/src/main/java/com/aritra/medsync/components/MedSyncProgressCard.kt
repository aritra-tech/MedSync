package com.aritra.medsync.components

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aritra.medsync.R
import com.aritra.medsync.ui.theme.medium16
import com.aritra.medsync.ui.theme.normal12

@Composable
fun MedSyncProgressCard() {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .height(220.dp),
        shape = RoundedCornerShape(10.dp),
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