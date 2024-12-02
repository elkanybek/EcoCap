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
import android.graphics.BitmapFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import com.example.ecocap.Data.Database.PointStore
import com.example.ecocap.Data.Database.QuestStore
import com.example.ecocap.ML_Kit.getImageLabels



@Composable
fun HistoryScreen(
    getHistory: suspend (userId: Int) -> List<PointStore>
) {
    var history by remember { mutableStateOf<List<PointStore>>(emptyList()) }

    LaunchedEffect(Unit) {
        history = getHistory(1)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "History",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Spacer(modifier = Modifier.height(16.dp))


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            userScrollEnabled = true,
        ) {
            items(history.size) { index ->

                Box(
                    modifier = Modifier
                        .width(375.dp)
                        .wrapContentHeight()
                        .padding(8.dp)
                        .shadow(8.dp, RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(8.dp)) // Apply rounded corners
                        .background(MaterialTheme.colorScheme.surfaceContainerHighest) // Background after clip
                ) {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Column {
                                Text(
                                    text = "Quest: ${history[index].questName}",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    fontFamily = FontFamily.Default,
                                    modifier = Modifier
                                        .align(Alignment.Start)
                                        .padding(8.dp)
                                )
                                Text(
                                    text = "Multiplier: x${history[index].streakMultiplier}",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    fontFamily = FontFamily.Default,
                                    modifier = Modifier
                                        .align(Alignment.Start)
                                        .padding(8.dp)
                                )
                            Text(
                                text = "Points: +${history[index].scoreGained}",
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontFamily = FontFamily.Default,
                                modifier = Modifier
                                    .align(Alignment.Start)
                                    .padding(8.dp),
                                softWrap = true

                            )
                        }
                        Row(modifier = Modifier.fillMaxHeight()) {
                            val imageData = history[index].image

                            if (imageData != null && imageData.isNotEmpty()) {
                                val bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
                                Image(
                                    bitmap = bitmap.asImageBitmap(),
                                    contentDescription = "Stored Image",
                                    modifier = Modifier
                                        .height(120.dp)
                                        .width(120.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .align(Alignment.CenterVertically)
                                )
                            } else {
                                // Display placeholder if no image is available
                                Image(
                                    painter = painterResource(id = R.drawable.flower),
                                    contentDescription = "Placeholder Image",
                                    modifier = Modifier
                                        .height(120.dp)
                                        .width(120.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .align(Alignment.CenterVertically)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


