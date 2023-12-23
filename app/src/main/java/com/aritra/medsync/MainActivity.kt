package com.aritra.medsync

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.aritra.medsync.navigation.MedSyncApp
import com.aritra.medsync.ui.theme.MedSyncTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedSyncTheme {
                MedSyncApp()
            }
        }
    }
}