package com.aritra.medsync.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.aritra.medsync.ui.theme.OnSurface20
import com.aritra.medsync.ui.theme.normal16
import com.aritra.medsync.utils.onClick

@Composable
fun SettingsItem(onClick: () -> Unit, iconId: Int, itemName: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .onClick { onClick() },
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = Color.Unspecified,
        )
        Text(
            text = itemName,
            color = OnSurface20,
            style = normal16
        )
    }
}