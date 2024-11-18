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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecocap.R
//
//@Composable
//fun CapturedImage(text: String = "Your Text Here") {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(top = 16.dp),
//        verticalArrangement = Arrangement.Center, // Centers children vertically
//        horizontalAlignment = Alignment.CenterHorizontally // Centers children horizontally
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.baseline_image_24),
//            contentDescription = "Captured Image",
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .height(400.dp) // Adjust height as needed
//                .fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(16.dp)) // Add space between Image and Button
//
//        Button(
//            onClick = { /* Replace with your actual onClick function */ },
//            modifier = Modifier.wrapContentSize()
//        ) {
//            Text(
//                text = "Capture Image",
//                fontSize = 18.sp,
//                color = Color.White
//            )
//        }
//
//        Spacer(modifier = Modifier.height(8.dp)) // Add space between Button and Text
//
//        Text(
//            text = text,
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.Blue
//        )
//    }
//}
//
//@Composable
//fun CaptureImageScreen(
//    initialText: String = "Your Text Here", // Initial value for the text
//    onCaptureClick: () -> Unit
//) {
//    // MutableState to hold the label text
//    var labelText by remember { mutableStateOf(initialText) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(top = 16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        // Placeholder image or actual captured image
//        Image(
//            painter = painterResource(id = R.drawable.baseline_image_24),
//            contentDescription = "Captured Image",
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .height(400.dp)
//                .fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Button to trigger capture
//        Button(
//            onClick = {
//                onCaptureClick() // Trigger capture logic
//                labelText = "Processing..." // Update text dynamically
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
//        // Dynamic text display
//        Text(
//            text = labelText, // Text updates dynamically
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.Blue
//        )
//    }
//}
