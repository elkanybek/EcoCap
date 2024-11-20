package com.example.ecocap.ML_Kit

import android.content.Context
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabel
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import java.io.IOException

//This function is for labeling purposes for the image
fun getImageLabels(context: Context, imageUri: Uri, callback: (List<ImageLabel>) -> Unit) {
    try {
        val image = InputImage.fromFilePath(context, imageUri)
        val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
        labeler.process(image)
            .addOnSuccessListener { labels ->
                callback(labels)
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                callback(emptyList()) // Return an empty list on failure
            }
    } catch (e: IOException) {
        e.printStackTrace()
        callback(emptyList()) // Return an empty list on exception
    }
}


