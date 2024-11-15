import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp


@Composable
fun ImageLabelingScreen() {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var labels by remember { mutableStateOf<List<String>>(emptyList()) }
    val context = LocalContext.current

    // Launch activity result API for image selection
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            selectedImageUri = uri
            uri?.let { analyzeImage(it, context, labels) }
        }
    )

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { launcher.launch("image/*") }) {
            Text(text = "Upload Image")
        }

        Spacer(modifier = Modifier.height(16.dp))

        selectedImageUri?.let {
            Image(painter = rememberImagePainter(it), contentDescription = "Selected Image")
        }

        Spacer(modifier = Modifier.height(16.dp))

        labels.takeIf { it.isNotEmpty() }?.let {
            Text("Labels: ${it.joinToString(", ")}")
        }
    }
}
