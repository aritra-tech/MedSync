package com.aritra.medsync.ui.screens.appointment.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.aritra.medsync.R
import com.aritra.medsync.domain.model.Appointment
import com.aritra.medsync.ui.theme.MedicineCircleColor
import com.aritra.medsync.ui.theme.OnPrimaryContainer
import com.aritra.medsync.ui.theme.OnSurface60
import com.aritra.medsync.ui.theme.medium16
import com.aritra.medsync.ui.theme.medium18
import com.aritra.medsync.ui.theme.normal14

@Composable
fun AppointmentCard(
    appointment: Appointment,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .then(modifier),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start,
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(MedicineCircleColor)
                        .padding(10.dp),
                    painter = painterResource(R.drawable.appointment_image),
                    contentDescription = null
                )

                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.Start,
                ) {

                    Text(
                        text = "Dr. ${appointment.doctorName}",
                        style = medium16.copy(color = OnPrimaryContainer)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row {
                        Text(
                            text = appointment.doctorSpecialization,
                            style = normal14.copy(color = OnSurface60)
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Row {
                            Image(
                                modifier = Modifier.size(16.dp),
                                painter = painterResource(R.drawable.reminder_icon),
                                contentDescription = null
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            Text(
                                text = appointment.appointmentTime,
                                style = normal14.copy(color = OnPrimaryContainer)
                            )
                        }
                    }
                }
            }
        }
    }
}