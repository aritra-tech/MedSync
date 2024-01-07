package com.aritra.medsync.components

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aritra.medsync.ui.theme.DMSansFontFamily
import com.aritra.medsync.ui.theme.Green
import com.aritra.medsync.ui.theme.OnSurface60

@Composable
fun MedSyncButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    trailingIcon : Painter ?= null,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .defaultMinSize(
                minWidth = ButtonDefaults.MinWidth,
                minHeight = 50.dp
            )
            .clip(RoundedCornerShape(100.dp))
            .then(modifier),
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Green
        ),
        enabled = enabled
    ) {
        Text(text = text, color = if (enabled) Color.White else OnSurface60, fontFamily = DMSansFontFamily, fontSize = 14.sp)
        trailingIcon?. let {
            Icon(modifier = Modifier.padding(start = 4.dp),painter = it, contentDescription = null)
        }
    }
}
