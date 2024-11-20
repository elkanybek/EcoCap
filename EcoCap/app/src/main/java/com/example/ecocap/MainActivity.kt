package com.example.ecocap

import QuestRepository
import QuestStore
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.AndroidViewModel
import com.example.ecocap.Data.quests
import com.example.ecocap.ui.theme.EcoCapTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EcoCapTheme {
                val db = AppDatabase.getInstance(applicationContext)
                val questRepository = QuestRepository(db.questDao())

                LaunchedEffect(Unit) {
                    launch(Dispatchers.IO) {
                        questRepository.insertQuests(quests)
                    }
                }
                QuestListScreen(questRepository)
            }
        }
    }
}

@Composable
fun QuestListScreen(questRepository: QuestRepository) {
    val questList = remember { mutableStateOf<List<QuestStore>>(emptyList()) }

    // Fetch data asynchronously when Composable is first launched
    LaunchedEffect(Unit) {

        // Fetch data using a coroutine
        questList.value = withContext(Dispatchers.IO) {
            questRepository.getAllQuests()
        }
    }

    // Display the list using LazyColumn
    LazyColumn {
        items(questList.value) { questStore ->
            QuestItem(questStore)
        }
    }
}

@Composable
fun QuestItem(questStore: QuestStore) {
    Column {
        Text(text = "ID: ${questStore.id}")
        Text(text = "Label: ${questStore.name}")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EcoCapTheme {
        Greeting("Android")
    }
}