package com.example.ecocap.ui.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecocap.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import com.example.ecocap.Data.Database.QuestStore

@Composable
fun HomeScreen(
    animals: List<String>,
    dailyStreak: Int,
    quests:  List<QuestStore>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Text(
            text = "EcoCap",
            fontSize = 80.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(35.dp))

        Row {
            Text(
                text = "DailyStreak: ${dailyStreak}",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Image(
                painter = painterResource(id = R.drawable.fire),
                contentDescription = "DailyStreak",
                modifier = Modifier.height(40.dp)
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

//        val quest = remember { mutableStateOf<QuestStore?>(null) }
//
//        LaunchedEffect(Unit) {
//            quest.value = getQuest()
//        }
//
//        Text(
//            text = "test: ${quest.value}"
//        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),

            horizontalAlignment = Alignment.CenterHorizontally,
            userScrollEnabled = true,
        ) {
            items(quests.size) { index ->
                Box(
                    modifier = Modifier
                        .width(375.dp)
                        .wrapContentHeight()
                        .padding(8.dp)
                        .shadow(8.dp, RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(8.dp)) // Apply rounded corners
                        .background(MaterialTheme.colorScheme.surfaceContainerHighest) // Background after clip
                ) {
                    Column {
                        Text(
                            text = quests[index].name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black ,
                            fontFamily = FontFamily.Default,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(8.dp)
                        )
                        Text(
                            text = "Capture ${quests[index].name} out in the wilderness, and upload it to EcoCap",
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


