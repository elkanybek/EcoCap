package com.example.ecocap.ui.Camera

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ecocap.ML_Kit.getImageLabels

class CaptureImageViewModel : ViewModel(){
    var selectedImageUri by mutableStateOf<Uri?>(null)
    var topLabel by mutableStateOf<String?>(null)

    fun processImage(context: Context, image: Uri?) {
        selectedImageUri = image
        if (selectedImageUri != null) {
            // Call the asynchronous function and update the state with the result
            getImageLabels(context, selectedImageUri!!) { labels ->
                topLabel = labels.maxByOrNull { it.confidence }?.text
            }
        }
    }
}