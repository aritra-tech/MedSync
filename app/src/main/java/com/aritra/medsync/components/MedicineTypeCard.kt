package com.aritra.medsync.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aritra.medsync.R
import com.aritra.medsync.domain.model.MedicineType
import com.aritra.medsync.ui.theme.MedicineCircleColor
import com.aritra.medsync.ui.theme.selectedBlue
import com.aritra.medsync.utils.onClick

@Composable
fun MedicineTypeCard(
    image: Painter,
    isSelected: Boolean,
    medicineType: MedicineType,
    onClick: (MedicineType) -> Unit
) {
    Box(
        modifier = Modifier
            .onClick {
                onClick(medicineType)
            },
        contentAlignment = Alignment.TopEnd
    ) {

        Column(
            modifier = Modifier
                .widthIn(min = 64.dp, 64.dp)
                .heightIn(min = 64.dp, 64.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(color = MedicineCircleColor)
                .border(
                    width = if (isSelected) 1.5.dp else 0.dp,
                    color = selectedBlue,
                    shape = RoundedCornerShape(100.dp)
                )
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                modifier = Modifier.size(30.dp),
                painter = image,
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun FilterCardsPreview() {
    MedicineTypeCard(
        image = painterResource(id = R.drawable.pill),
        isSelected = true,
        medicineType = MedicineType.CAPSULE
    ) {}
}