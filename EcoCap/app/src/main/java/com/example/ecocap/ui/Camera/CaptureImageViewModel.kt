package com.example.ecocap.ui.Camera

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import coil.compose.AsyncImage
import com.example.ecocap.R
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import okio.IOException
import android.content.Context
import android.content.Context.CAMERA_SERVICE
import com.google.mlkit.vision.label.ImageLabel

class CaptureImageViewModel : ViewModel() {
    // State to hold the label text
    private val _labelText = mutableStateOf("Your Text Here")
    val labelText: State<String> = _labelText

    // Function to update the label text
    fun updateLabelText(newText: String) {
        _labelText.value = newText
    }
}
//
//@Composable
//fun CaptureImageScreen(viewModel: CaptureImageViewModel, onCaptureClick: () -> Unit) {
//    // Observe the labelText state from the ViewModel
//    val labelText by viewModel.labelText
//    val imageResource = remember { mutableStateOf(R.drawable.baseline_image_24) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(top = 16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//
//
//        // Display image placeholder
//        Image(
//            painter = painterResource(id = imageResource.value),
//            contentDescription = "Captured Image",
//            modifier = Modifier
//                .height(400.dp)
//                .fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Button to trigger image capture
//        Button(
//            onClick = {
//                onCaptureClick() // Trigger the capture action
//                viewModel.updateLabelText("Processing...") // Update label text
//            },
//            modifier = Modifier.wrapContentSize()
//        ) {
//            Text(
//                text = "Capture Image",
//                fontSize = 18.sp,
//                color = Color.White
//            )
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        // Display dynamic label text
//        Text(
//            text = labelText, // Use the observed state directly
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.Blue
//        )
//    }
//}
//
//@Composable
//fun CaptureImageScreen(
//    viewModel: CaptureImageViewModel,
//    onCaptureClick: () -> Unit,
//    capturedImage: Bitmap?
//) {
//    val labelText by viewModel.labelText
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(top = 16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        // Display captured image or placeholder
//        if (capturedImage != null) {
//            Image(
//                bitmap = capturedImage.asImageBitmap(),
//                contentDescription = "Captured Image",
//                modifier = Modifier
//                    .height(400.dp)
//                    .fillMaxWidth()
//            )
//        } else {
//            Image(
//                painter = painterResource(id = R.drawable.baseline_image_24),
//                contentDescription = "Placeholder Image",
//                modifier = Modifier
//                    .height(400.dp)
//                    .fillMaxWidth()
//            )
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Button to capture image
//        Button(
//            onClick = {
//                onCaptureClick()
//                viewModel.updateLabelText("Processing...")
//            },
//            modifier = Modifier.wrapContentSize()
//        ) {
//            Text(
//                text = "Capture Image",
//                fontSize = 18.sp,
//                color = Color.White
//            )
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        // Display label text
//        Text(
//            text = labelText,
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.Blue
//        )
//    }
//}
//

@Composable
fun CaptureImageScreen(
    viewModel: CaptureImageViewModel,
    onCaptureClick: () -> Unit,
    context: Context,
    capturedImage: Bitmap?
) {
    val labelText by viewModel.labelText

    var selectedImageUri by remember {mutableStateOf<Uri?>(null)}
    var imageLabels by remember {mutableStateOf<List<ImageLabel>>(emptyList())}

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            selectedImageUri = it
            if(selectedImageUri != null){
                imageLabels = getImageLabels(context, selectedImageUri as Uri)
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (selectedImageUri != null) {
            // Display the captured image
//            Image(
//                bitmap = capturedImage.asImageBitmap(),
//                contentDescription = "Captured Image",
//                modifier = Modifier
//                    .height(400.dp)
//                    .fillMaxWidth()
//            )
            AsyncImage(
                modifier = Modifier.height(400.dp).fillMaxWidth(),
                model = selectedImageUri,
                contentDescription = null
            )
        } else {
            // Display placeholder
            Image(
                painter = painterResource(id = R.drawable.baseline_image_24),
                contentDescription = "Placeholder Image",
                modifier = Modifier
                    .height(400.dp)
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onCaptureClick,
            modifier = Modifier.wrapContentSize()
        ) {
            Text(
                text = "Capture Image",
                fontSize = 18.sp,
                color = Color.White
            )
        }

        Button(
            onClick = {
                photoPicker.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            },
            modifier = Modifier.wrapContentSize()
        ) {
            Text(
                text = "Upload Image",
                fontSize = 18.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = labelText,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )
    }
}

fun getImageLabels(context: Context, imageUri: Uri) : List<ImageLabel>{
    var imageLabels: List<ImageLabel> = emptyList()
    val image: InputImage
    try {
        image = InputImage.fromFilePath(context, imageUri)
        val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
        labeler.process(image)
            .addOnSuccessListener { labels ->
                imageLabels = labels
            }
            .addOnFailureListener { e ->
                // Task failed with an exception
                // ...
            }
    } catch (e: IOException) {
        e.printStackTrace()
    }
œ
    return imageLabels
}

