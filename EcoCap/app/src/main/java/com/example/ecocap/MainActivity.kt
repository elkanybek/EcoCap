package com.example.ecocap

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.ecocap.ui.Camera.CaptureImageScreen
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabel
import com.google.mlkit.vision.label.ImageLabeler
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.ecocap.ui.Camera.CaptureImageViewModel
//
//class MainActivity : ComponentActivity() {
//
//    private lateinit var objectImage: ImageView
//    private lateinit var labelText: TextView
//    private lateinit var captureImgBtn: Button
//    private lateinit var imageLabeler: ImageLabeler
//    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
//
//    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
////            EcoCapTheme {
////                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
////                    Greeting(
////                        name = "Android",
////                        modifier = Modifier.padding(innerPadding)
////                    )
////                }
////            }
//
//            val captureImageViewModel = ViewModelProvider(this).get(CaptureImageViewModel::class.java)
//
//            CaptureImageScreen(
//                viewModel = captureImageViewModel,
//                onCaptureClick = {
//                    // Implement any action you want to perform when the capture button is clicked
//                    // For example, you could start the camera intent or update the label
//                    captureImageViewModel.updateLabelText("Image captured!")
//                }
//            )
//
//        }
////        objectImage = findViewById(R.id.objectImage)
////        labelText = findViewById(R.id.labelText)
////        captureImgBtn = findViewById(R.id.captureImgBtn)
//
//
//        checkCameraPermission()
//        imageLabeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
//
//        cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
//            if(result.resultCode == Activity.RESULT_OK){
//                val extras = result.data?.extras
//                val imageBitmap = extras?.getParcelable("data", Bitmap::class.java)
//                if(imageBitmap != null){
//                    objectImage.setImageBitmap(imageBitmap)
//                    labelImage(imageBitmap)
//                }else{
//                    labelText.text = "Unable to capture image"
//                }
//            }
//        }
//
//        captureImgBtn.setOnClickListener{
//            val clickPicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            if(clickPicture.resolveActivity(packageManager) != null){
//                cameraLauncher.launch(clickPicture)
//            }
//        }
//    }
//
//    private fun labelImage(bitmap: Bitmap){
//        val inputImage = InputImage.fromBitmap(bitmap, 0)
//
//        imageLabeler.process(inputImage).addOnSuccessListener { label ->
//            displayLabel(label)
//        }.addOnFailureListener{e ->
//            labelText.text = "Error: ${e.message}"
//        }
//    }
//
//    private fun displayLabel(labels: List<ImageLabel>){
//        if(labels.isNotEmpty()){
//            val mostConfidentLabel = labels[0]
//            labelText.text = "${mostConfidentLabel.text}"
//        } else{
//            labelText.text = "No labels found"
//        }
//    }
//
//    private fun checkCameraPermission(){
//        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 1)
//        }
//    }
//}
//
//class MainActivity : ComponentActivity() {
//
//    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
//    private var capturedImageBitmap: Bitmap? = null
//
//    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        // Register camera launcher
//        cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                val extras = result.data?.extras
//                val imageBitmap = extras?.getParcelable<Bitmap>("data")
//                capturedImageBitmap = imageBitmap
//            }
//        }
//
//        setContent {
//            val viewModel = ViewModelProvider(this).get(CaptureImageViewModel::class.java)
//
//            CaptureImageScreen(
//                viewModel = viewModel,
//                onCaptureClick = {
//                    // Trigger camera intent
//                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                    if (intent.resolveActivity(packageManager) != null) {
//                        cameraLauncher.launch(intent)
//                    } else {
//                        viewModel.updateLabelText("Camera not available")
//                    }
//                },
//                capturedImage = capturedImageBitmap
//            )
//        }
//
//        checkCameraPermission()
//    }
//
//    private fun checkCameraPermission() {
//        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 1)
//        }
//    }
//}
//

class MainActivity : ComponentActivity() {

    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private var capturedImageBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Register camera launcher
        cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val extras = result.data?.extras
                val imageBitmap = extras?.getParcelable<Bitmap>("data")
                if (imageBitmap != null) {
                    capturedImageBitmap = imageBitmap
                } else {
                    Log.e("Camera", "No image captured")
                }
            } else {
                Log.e("Camera", "Result code: ${result.resultCode}")
            }
        }

        setContent {
            val viewModel = ViewModelProvider(this).get(CaptureImageViewModel::class.java)

            CaptureImageScreen(
                viewModel = viewModel,
                onCaptureClick = {
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    if (cameraIntent.resolveActivity(packageManager) != null) {
                        cameraLauncher.launch(cameraIntent)
                    } else {
                        viewModel.updateLabelText("Camera not available")
                    }
                },
                capturedImage = capturedImageBitmap
            )
        }

        checkCameraPermission()
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 1)
        }
    }
}

