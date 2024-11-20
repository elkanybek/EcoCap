package com.example.ecocap.ui.Screens

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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.text.style.TextDecoration
import com.example.ecocap.ML_Kit.getImageLabels

@Composable
fun HomeScreen(
//    context: Context,
) {
//    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
//    var topLabel by remember { mutableStateOf<String?>(null) }
//
//    val photoPicker = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.PickVisualMedia(),
//        onResult = {
//            selectedImageUri = it
//            if (selectedImageUri != null) {
//                // Call the asynchronous function and update the state with the result
//                getImageLabels(context, selectedImageUri!!) { labels ->
//                    topLabel = labels.maxByOrNull { it.confidence }?.text
//                }
//            }
//        }
//    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 64.dp),
//        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

//        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "EcoCap",
            fontSize = 80.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(35.dp))

        Row {
            Text(
                text = "DailyStreak: 3",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
//            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(id = R.drawable.fire),
                contentDescription = "DailyStreak",
                modifier = Modifier.height(30.dp)
            )
        }

        Spacer(modifier = Modifier.height(35.dp))

        Text(
            text = "Today's Quests",
            fontSize = 40.sp,
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Bold,
            color = Color.Black,

        )

        LazyColumn {
            items(5) { index ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color.Gray)
                ) {
                    Text(
                        text = "Quest $index",
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

    }
}


