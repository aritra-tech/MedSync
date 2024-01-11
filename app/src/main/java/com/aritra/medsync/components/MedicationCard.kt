package com.aritra.medsync.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.aritra.medsync.R
import com.aritra.medsync.domain.model.Medication
import com.aritra.medsync.ui.theme.OnPrimaryContainer
import com.aritra.medsync.ui.theme.bold18
import com.aritra.medsync.ui.theme.medium14
import com.aritra.medsync.ui.theme.medium16

@Composable
fun MedicationCard(
    medication: Medication
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Image(
            modifier = Modifier.size(40.dp),
            painter = getMedicineImage(medication.medicineType),
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = medication.pillsAmount.toString()+ " " + "pills",
                style = medium14.copy(color = OnPrimaryContainer)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = medication.medicineName,
                style = bold18.copy(color = OnPrimaryContainer)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier.alpha(0.75f),
                text = "11:00 AM",
                style = medium16.copy(color = OnPrimaryContainer)
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        
    }
}
@Composable
fun getMedicineImage(medicineType: String): Painter {
    return when(medicineType) {
        "TABLET" -> painterResource(id = R.drawable.pill)
        "CAPSULE" -> painterResource(id = R.drawable.capsule)
        "SYRUP" -> painterResource(id = R.drawable.amp)
        "INHALER" -> painterResource(id = R.drawable.inahler)
        else -> painterResource(id = R.drawable.ic_launcher_foreground) // TODO: Need to change the image
    }
}
