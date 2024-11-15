package com.example.ecocap.ML_Kit

import android.content.Context
import android.net.Uri
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.*
import com.google.mlkit.vision.common.InputImage
import java.io.IOException

fun analyzeImage(uri: Uri, context: Context, labels: MutableState<List<String>>) {
    try {
        val bitmap = uriToBitmap(context, uri)
        val inputImage = InputImage.fromBitmap(bitmap, 0)

        val labeler: ImageLabeler = ImageLabeling.getClient()

        labeler.process(inputImage)
            .addOnSuccessListener { imageLabels ->
                // Handle successful label detection
                labels.value = imageLabels.map { it.text }
            }
            .addOnFailureListener { e ->
                // Handle failure
                labels.value = listOf("Error: ${e.message}")
            }
    } catch (e: IOException) {
        labels.value = listOf("Error: ${e.message}")
    }
}

fun uriToBitmap(context: Context, uri: Uri): Bitmap {
    return context.contentResolver.openInputStream(uri)?.use { inputStream ->
        BitmapFactory.decodeStream(inputStream)
    } ?: throw IOException("Failed to decode image")
}