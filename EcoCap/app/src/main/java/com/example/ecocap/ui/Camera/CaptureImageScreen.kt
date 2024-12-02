package com.example.ecocap.ui.Camera

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ecocap.R
import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import com.example.ecocap.ML_Kit.getImageLabels

@Composable
fun CaptureImageScreen(
    context: Context,
    selectedImageUri: Uri?,
    imageLabel: List<String>?,
    setImage: (context: Context, image: Uri?) -> Unit,
    checkResult: () -> Unit
) {
    //var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    //var topLabel by remember { mutableStateOf<String?>(null) }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            setImage(context, it)
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
            AsyncImage(
                modifier = Modifier
                    .height(400.dp)
                    .fillMaxWidth(),
                model = selectedImageUri,
                contentDescription = null
            )
        } else {
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
            onClick = {
                photoPicker.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            },
            modifier = Modifier.wrapContentSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,

            ){
                Icon(Icons.Filled.Search, contentDescription = "Upload", Modifier.size(40.dp))
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "Upload Image",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Display the top label
        imageLabel?.let {
//            if (it.size >= 3) {
                Text(
                    text = it.joinToString(", "),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue
                )
//            }
        }


        if (selectedImageUri != null){
            Button(onClick = {checkResult()}){
                Text("Check Result")
            }
        }
    }
}

//
////Good , keep depending of the number of labels we want
//@Composable
//fun CaptureImageScreen(
//    viewModel: CaptureImageViewModel,
//    onCaptureClick: () -> Unit,
//    context: Context,
//    capturedImage: Bitmap?
//) {
//    val labelText by viewModel.labelText
//
//    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
//    var imageLabels by remember { mutableStateOf<List<ImageLabel>>(emptyList()) }
//
//    val photoPicker = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.PickVisualMedia(),
//        onResult = {
//            selectedImageUri = it
//            if (selectedImageUri != null) {
//                // Call the asynchronous function and update the state with the result
//                com.example.ecocap.ML_Kit.getImageLabels(context, selectedImageUri!!) { labels ->
//                    imageLabels = labels
//                }
//            }
//        }
//    )
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(top = 16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        if (selectedImageUri != null) {
//            AsyncImage(
//                modifier = Modifier
//                    .height(400.dp)
//                    .fillMaxWidth(),
//                model = selectedImageUri,
//                contentDescription = null
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
//        Button(
//            onClick = onCaptureClick,
//            modifier = Modifier.wrapContentSize()
//        ) {
//            Text(
//                text = "Capture Image",
//                fontSize = 18.sp,
//                color = Color.White
//            )
//        }
//
//        Button(
//            onClick = {
//                photoPicker.launch(
//                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
//                )
//            },
//            modifier = Modifier.wrapContentSize()
//        ) {
//            Text(
//                text = "Upload Image",
//                fontSize = 18.sp,
//                color = Color.White
//            )
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        // Display the list of labels
//        imageLabels.forEach { label ->
//            Text(
//                text = "Label: ${label.text}, Confidence: ${label.confidence}",
//                fontSize = 16.sp,
//                color = Color.Black
//            )
//        }
//
//        Text(
//            text = labelText,
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.Blue
//        )
//    }
//}
//
//fun getImageLabels(context: Context, imageUri: Uri, callback: (List<ImageLabel>) -> Unit) {
//    try {
//        val image = InputImage.fromFilePath(context, imageUri)
//        val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
//        labeler.process(image)
//            .addOnSuccessListener { labels ->
//                callback(labels)
//            }
//            .addOnFailureListener { e ->
//                e.printStackTrace()
//                callback(emptyList()) // Return an empty list on failure
//            }
//    } catch (e: IOException) {
//        e.printStackTrace()
//        callback(emptyList()) // Return an empty list on exception
//    }
//}
