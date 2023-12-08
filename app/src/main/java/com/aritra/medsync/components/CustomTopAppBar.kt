package com.aritra.medsync.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.aritra.medsync.ui.theme.BackgroundDark
import com.aritra.medsync.ui.theme.OnBackgroundDark
import com.aritra.medsync.ui.theme.normal22

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String,
    shouldShowBackButton: Boolean = true,
    onBackPress: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = normal22,
            )
        },
        navigationIcon = {
            if (shouldShowBackButton) {
                IconButton(onClick = {
                    onBackPress()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        },
    )
}

@Preview
@Composable
fun CustomTopAppBarPreview() {
    CustomTopAppBar("Title goes here") {}
}