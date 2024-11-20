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
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import com.example.ecocap.ML_Kit.getImageLabels


val animals = listOf(
    "Frog",
    "Tiger",
    "Elephant",
    "Penguin",
    "Panda",
    "Koala",
    "Giraffe",
    "Lion",
    "Zebra",
    "Kangaroo",
    "Polar Bear"
)

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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),

            horizontalAlignment = Alignment.CenterHorizontally,
            userScrollEnabled = true,
        ) {
            items(10) { index ->
                Box(
                    modifier = Modifier
                        .width(375.dp)
                        .wrapContentHeight()
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp)) // Apply rounded corners
                        .background(MaterialTheme.colorScheme.surfaceContainerHighest) // Background after clip
                ) {
                    Column {
                        Text(
                            text = "${animals[index]}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black ,
                            fontFamily = FontFamily.Default,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(8.dp)
                        )
                        Text(
                            text = "Find a ${animals[index]} out in the wilderness, and upload it to EcoCap",
                            fontSize = 20.sp,
                            color = Color.Black ,
                            fontFamily = FontFamily.Default,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(8.dp),
                            softWrap = true

                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(35.dp))

    }
}

