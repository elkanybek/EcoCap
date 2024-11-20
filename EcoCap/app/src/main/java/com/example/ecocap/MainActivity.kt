package com.example.ecocap

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.ecocap.ui.Camera.CaptureImageScreen
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CaptureImageScreen(
                context = this
            )
        }
    }
}

