package com.example.ecocap.ui.Camera

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CaptureImageViewModel : ViewModel(){
    var selectedImageUri by mutableStateOf<Uri?>(null)
    var topLabel by mutableStateOf<String?>(null)
}