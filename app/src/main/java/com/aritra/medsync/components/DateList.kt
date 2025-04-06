package com.aritra.medsync.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aritra.medsync.domain.model.CalendarModel
import com.aritra.medsync.utils.toFormattedDateShortString

@Composable
fun DateList(
    data: CalendarModel,
    onDateClicked: (CalendarModel.DateModel) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items(items = data.visibleDates) {
            DateItem(it, onDateClicked)
        }
    }
}

@Composable
fun DateItem(
    date: CalendarModel.DateModel,
    onClicked: (CalendarModel.DateModel) -> Unit
) {
    Column {
        Text(
            text = date.day,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.outline
        )
        Card(
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 4.dp)
                .clickable { onClicked(date) },
            colors = CardDefaults.cardColors(
                containerColor = when {
                    date.isSelected -> MaterialTheme.colorScheme.tertiary
                    date.isToday -> MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f)
                    else -> MaterialTheme.colorScheme.surface
                }
            ),
            border = if (date.isToday && !date.isSelected) {
                BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary)
            } else {
                null
            }
        ) {
            Column(
                modifier = Modifier
                    .width(42.dp)
                    .height(42.dp)
                    .padding(8.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = date.date.toFormattedDateShortString(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = if (date.isSelected || date.isToday) {
                        FontWeight.Medium
                    } else {
                        FontWeight.Normal
                    },
                    color = when {
                        date.isSelected -> MaterialTheme.colorScheme.onTertiary
                        date.isToday -> MaterialTheme.colorScheme.onTertiaryContainer
                        else -> MaterialTheme.colorScheme.onSurface
                    }
                )
            }
        }
    }
}
