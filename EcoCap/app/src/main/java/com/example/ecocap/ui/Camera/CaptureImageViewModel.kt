package com.example.ecocap.ui.Camera

import android.graphics.Bitmap
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
import com.example.ecocap.R

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
    capturedImage: Bitmap?
) {
    val labelText by viewModel.labelText

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (capturedImage != null) {
            // Display the captured image
            Image(
                bitmap = capturedImage.asImageBitmap(),
                contentDescription = "Captured Image",
                modifier = Modifier
                    .height(400.dp)
                    .fillMaxWidth()
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

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = labelText,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )
    }
}

