//import com.google.mlkit.vision.common.InputImage
//import com.google.mlkit.vision.label.ImageLabeling
//import android.graphics.BitmapFactory
//import android.net.Uri
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.height
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import com.google.mlkit.vision.label.ImageLabel
//import com.google.mlkit.vision.label.ImageLabeler
//import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
//import com.google.mlkit.vision.objects.ObjectDetection
//import com.google.mlkit.vision.objects.ObjectDetector
//import com.google.mlkit.vision.objects.DetectedObject
//import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions
//
//// Image Labeling Process
//
//@Composable
//fun ProcessImageLabeling(imageUri: Uri) {
//    val context = LocalContext.current
//    var labels by remember { mutableStateOf<List<ImageLabel>>(emptyList()) }
//    var isProcessing by remember { mutableStateOf(false) }
//
//    LaunchedEffect(imageUri) {
//        isProcessing = true
//
//        // Convert URI to Bitmap
//        val bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(imageUri))
//
//        // Create InputImage from Bitmap
//        val image = InputImage.fromBitmap(bitmap, 0)
//
//        // Initialize the labeler using default options
//        val labeler: ImageLabeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
//
//        // Process the image
//        labeler.process(image)
//            .addOnSuccessListener { result ->
//                // Handle the result, which is a List<ImageLabel>
//                labels = result
//                isProcessing = false
//            }
//            .addOnFailureListener { e ->
//                // Handle the error
//                e.printStackTrace()
//                isProcessing = false
//            }
//    }
//
//    // Show labels and a loading state
//    if (isProcessing) {
//        CircularProgressIndicator()
//    } else {
//        Column {
//            Text("Labels:")
//            labels.forEach { label ->
//                Text("Label: ${label.text}, Confidence: ${label.confidence}")
//            }
//        }
//    }
//}
//
//@Composable
//fun ProcessObjectDetection(imageUri: Uri) {
//    val context = LocalContext.current
//    var objects by remember { mutableStateOf<List<DetectedObject>>(emptyList()) }
//    var isProcessing by remember { mutableStateOf(false) }
//
//    LaunchedEffect(imageUri) {
//        isProcessing = true
//
//        // Convert URI to Bitmap
//        val bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(imageUri))
//
//        // Create InputImage from Bitmap
//        val image = InputImage.fromBitmap(bitmap, 0)
//
//        // Create ObjectDetectorOptions
//        val options = ObjectDetectorOptions.Builder()
//            .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE) // Use single image mode
//            //.setMultipleObjectsEnabled(true) // Detect multiple objects
//            .build()
//
//        // Set up the object detector with options
//        val detector: ObjectDetector = ObjectDetection.getClient(options)
//
//        // Process the image
//        detector.process(image)
//            .addOnSuccessListener { detectedObjects ->
//                // Set detected objects
//                objects = detectedObjects
//                isProcessing = false
//            }
//            .addOnFailureListener { e ->
//                // Handle error
//                e.printStackTrace()
//                isProcessing = false
//            }
//    }
//
//    // Show the processing state and detected objects
//    if (isProcessing) {
//        CircularProgressIndicator()
//    } else {
//        Column {
//            Text("Detected Objects:")
//            objects.forEach { detectedObject ->
//                Text("Object with labels: ${detectedObject.labels.joinToString(", ")}")
//            }
//        }
//    }
//}
