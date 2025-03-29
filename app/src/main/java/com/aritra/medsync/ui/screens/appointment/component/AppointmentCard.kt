package com.aritra.medsync.ui.screens.appointment.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aritra.medsync.domain.model.Appointment
import com.aritra.medsync.ui.theme.OnPrimaryContainer
import com.aritra.medsync.ui.theme.OnSurface60
import com.aritra.medsync.ui.theme.medium18
import com.aritra.medsync.ui.theme.normal14

@Composable
fun AppointmentCard(
    appointment: Appointment,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
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
//                Image(
//                    modifier = Modifier
//                        .size(50.dp)
//                        .clip(RoundedCornerShape(16.dp))
//                        .background(MedicineCircleColor)
//                        .padding(10.dp),
//                    painter = getMedicineImage(medication.medicineType),
//                    contentDescription = null
//                )

                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.Start,
                ) {

                    Text(
                        text = "Dr. ${appointment.doctorName}",
                        style = medium18.copy(color = OnPrimaryContainer)
                    )


                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = appointment.doctorSpecialization,
                        style = normal14.copy(color = OnSurface60)
                    )
                }
            }
        }
    }
}