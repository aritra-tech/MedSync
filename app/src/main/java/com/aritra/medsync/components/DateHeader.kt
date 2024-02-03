package com.aritra.medsync.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aritra.medsync.R
import com.aritra.medsync.domain.model.CalendarModel
import com.aritra.medsync.ui.theme.OnPrimaryContainer
import com.aritra.medsync.ui.theme.normal20
import com.aritra.medsync.utils.toFormattedMonthDateString
import java.util.Date

@Composable
fun DateHeader(
    data: CalendarModel,
    onPrevClick: (Date) -> Unit,
    onNextClick: (Date) -> Unit
) {
    Row(
        modifier = Modifier.padding(horizontal = 8.dp,vertical = 8.dp)
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            text = if (data.selectedDate.isToday) {
                stringResource(R.string.today)
            } else {
                data.selectedDate.date.toFormattedMonthDateString()
            },
            style = normal20,
            color = OnPrimaryContainer
        )
        IconButton(
            onClick = { onPrevClick(data.startDate.date) })
        {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Filled.ArrowBackIos,
                contentDescription = null
            )
        }
        IconButton(
            onClick = { onNextClick(data.endDate.date) })
        {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Filled.ArrowForwardIos,
                contentDescription = null
            )
        }
    }
}