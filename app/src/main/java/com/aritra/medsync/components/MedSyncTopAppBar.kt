package com.aritra.medsync.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.aritra.medsync.R
import com.aritra.medsync.ui.theme.Background
import com.aritra.medsync.ui.theme.DMSansFontFamily
import com.aritra.medsync.ui.theme.normal22

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun MedSyncTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    navigationIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    shouldShowBackButton: Boolean = true,
    shouldShowFavAndEditButton: Boolean = true,
    fontSize: TextUnit = 22.sp,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(Background),
    onBackPress: () -> Unit = {},
    onEditPress: () -> Unit = {},
    onDeletePress: () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                fontFamily = DMSansFontFamily,
                fontSize = fontSize,
                color = Color.Black,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        },
        navigationIcon = {
            if (shouldShowBackButton) {
                IconButton(onClick = {
                    onBackPress()
                }) {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = null
                    )
                }
            }
        },
        actions = {
            if (shouldShowFavAndEditButton.not()) {
                IconButton(onClick = {
                    onEditPress()
                }) {
                    Icon(painter = painterResource(id = R.drawable.edit_icon), contentDescription = null)
                }

                IconButton(onClick = {
                    onDeletePress()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.delete_icon),
                        contentDescription = null
                    )
                }
            }
        },
        colors = colors
    )
}
