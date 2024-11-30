package com.example.ecocap

import QuestRepository
import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Build
//import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.activity.ComponentActivity
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ecocap.ui.Screens.HistoryScreen
import com.example.ecocap.ui.Screens.HomeScreen
import com.example.ecocap.ui.theme.EcoCapTheme
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ecocap.Data.Database.DatabaseProvider
import com.example.ecocap.Data.Database.QuestStore
import com.example.ecocap.Data.quests
import com.example.ecocap.ui.Camera.CaptureImageScreen
import com.example.ecocap.ui.Camera.CaptureImageViewModel
import com.example.ecocap.ui.Screens.HistoryViewModel
import com.example.ecocap.ui.Screens.HomeViewModel
import com.example.ecocap.ui.Screens.ResultViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//class MainActivity : ComponentActivity() {
//    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        setContent {
//            CaptureImageScreen(
//                context = this
//            )
//        }
//    }
//}

val LocalNavController = compositionLocalOf<NavController> { error("No NavController found!") }

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val db = DatabaseProvider.AppDatabase.getInstance(applicationContext)
            val questRepository = QuestRepository(db.questDao())

            LaunchedEffect(Unit) {
                launch(Dispatchers.IO) {
                    questRepository.insertQuests(quests)
                }
            }

            val homeViewModel: HomeViewModel by viewModels{
                HomeViewModelFactory(questRepository)
            }
            val historyViewModel: HistoryViewModel by viewModels()
            val resultViewModel: ResultViewModel by viewModels()
            var isDarkTheme by remember { mutableStateOf(false) }

            val captureImageViewModel: CaptureImageViewModel by viewModels()



            EcoCapTheme(darkTheme = isDarkTheme) {
                Router(this, captureImageViewModel, homeViewModel, historyViewModel, resultViewModel)
            }
        }
    }
}

/**
 * Router for the Quiz App.
 * Handles navigation between screens.
 *
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Router(
    context: Context,
    captureImageViewModel: CaptureImageViewModel,
    homeViewModel: HomeViewModel,
    historyViewModel: HistoryViewModel,
    resultViewModel: ResultViewModel
) {
    val navController = rememberNavController()
    var canNavigateBack by rememberSaveable { mutableStateOf(false) }
    var inSettingsScreen by rememberSaveable { mutableStateOf(false) }

    var quests by remember { mutableStateOf<List<QuestStore>>(emptyList()) }

    LaunchedEffect(Unit) {
        quests = homeViewModel.getQuests()
    }

    TopBottomBar(navController) {
        CompositionLocalProvider(LocalNavController provides navController) {
            NavHost(navController = navController, startDestination = "HomeScreenRoute",  enterTransition = { slideInHorizontally { length -> length } }, exitTransition = { slideOutHorizontally { length -> -length } }) {

                composable("HomeScreenRoute") {
                    HomeScreen(animals = homeViewModel.animals,
                        dailyStreak = homeViewModel.dailyStreak,
                        quests
                    )
                }
                composable("HistoryScreenRoute") {
                    HistoryScreen(animals = historyViewModel.animals)
                }
                composable("CaptureScreenRoute") {
                    CaptureImageScreen(
                        context = context,
                        selectedImageUri = captureImageViewModel.selectedImageUri,
                        imageLabel = captureImageViewModel.topLabel,
                        setImage = { context: Context, image: Uri? -> captureImageViewModel.processImage(context, image) }
                    )
                }
                composable("ScoreScreenRoute/{score}") {
                    val score: String = it.arguments?.getString("score") ?: ""
                }
            }
        }
    }


}

class HomeViewModelFactory(private val questRepository: QuestRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(questRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


//@SuppressLint("UnrememberedMutableState", "UnusedMaterial3ScaffoldPaddingParameter")
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun Router(
//    context: Context
//) {
//
//    val thisContext = context;
//    val navController = rememberNavController()
//    var canNavigateBack by rememberSaveable() { mutableStateOf(false) }
//    var inSettingsScreen by rememberSaveable() { mutableStateOf(false) }
//
//    Surface(
//        color = Color(0xFFBA1A1A),
//        modifier = Modifier.fillMaxSize()
//    ) {
//        Scaffold(
//            topBar = {
//                TopAppBar(
//                    modifier = Modifier.padding(0.dp),
//                    colors = topAppBarColors(
//                        containerColor = MaterialTheme.colorScheme.tertiary,
//                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
//                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary       // Sets action icon color
//                    ),
//                    title = {
////                        Row {
////                            val image = painterResource(R.drawable.logo)
////                            Image(
////                                painter = image,
////                                contentDescription = "EcoCap",
////                                modifier = Modifier
////                                    .size(40.dp)
////                                    .padding(8.dp)
////                            )
//                            Text( "EcoCap")
////                        }
//                    },
//                    actions = {
//                        IconButton(
//                            onClick = { navController.navigate("SettingsScreenRoute")},
//                            enabled = !inSettingsScreen
//                        ) {
//                            Image(
//                                painter = painterResource(R.drawable.logo),
//                                contentDescription = "Settings",
//                                modifier = Modifier.size(300.dp).padding(0.dp)
//                            )
//                        }
//
//                    },
//
//
//                )
//            },
//            bottomBar = {
//                BottomAppBar(
//                    actions = {
//                        Row (
//                            horizontalArrangement = Arrangement.SpaceBetween,
//                            modifier = Modifier.fillMaxWidth()
//                        ){
//                            //1
//                            IconButton(
//                                onClick = { navController.navigate("HomeScreenRoute")},
////                                enabled = canNavigateBack,
//                                modifier = Modifier
//                                    .size(56.dp)
//                                    .clip(CircleShape)
//                            ) {
//                                Icon(Icons.Rounded.Home, contentDescription = "Home", Modifier.size(40.dp))
//                            }
//
//                            //2
//                            IconButton(
//                                onClick = { navController.navigate("HistoryScreenRoute")},
////                                enabled = canNavigateBack,
//                                modifier = Modifier
//                                    .size(56.dp)
//                                    .clip(CircleShape)
//                            ) {
//                                Icon(Icons.Filled.DateRange, contentDescription = "History", Modifier.size(40.dp))
//                            }
//
//                            //3
//                            IconButton(
//                                onClick = { navController.navigate("CaptureScreenRoute")},
////                                enabled = canNavigateBack,
//                                modifier = Modifier
//                                    .size(56.dp)
//                                    .clip(CircleShape)
//                            ) {
//                                Icon(Icons.Filled.AddCircle, contentDescription = "Caputre", Modifier.size(40.dp))
//                            }
//
//                            //4
//                            IconButton(
////                                onClick = { navController.popBackStack(); inSettingsScreen = false },
//                                onClick = { },
////                                enabled = canNavigateBack,
//                                modifier = Modifier
//                                    .size(56.dp)
//                                    .clip(CircleShape)
//                            ) {
//                                Icon(Icons.Filled.AccountCircle, contentDescription = "Profile", Modifier.size(40.dp))
//                            }
//
//                            //5
//                            IconButton(
////                                onClick = { navController.popBackStack(); inSettingsScreen = false },
//                                onClick = { },
////                                enabled = canNavigateBack,
//                                modifier = Modifier
//                                    .size(56.dp)
//                                    .clip(CircleShape)
//                            ) {
//                                Icon(Icons.Filled.Settings, contentDescription = "Settings", Modifier.size(40.dp))
//                            }
//                        }
//                    },
//                    containerColor = MaterialTheme.colorScheme.tertiary,
//                )
//            },
//            content = {
//                CompositionLocalProvider(LocalNavController provides navController) {
//                    NavHost(navController = navController, startDestination = "HomeScreenRoute",  enterTransition = { slideInHorizontally { length -> length } }, exitTransition = { slideOutHorizontally { length -> -length } }) {
//
//
//                        composable("HomeScreenRoute")
//                        {
//                            HomeScreen()
//                        }
//
//                        composable("HistoryScreenRoute")
//                        {
//                            HistoryScreen()
////                            canNavigateBack = navController.previousBackStackEntry != null
////                            SettingsScreen(
////                                onThemeChange = onThemeChange,
////                                isInSettings = { inSettingsScreen = it }
////                            )
//                        }
//
//                        composable("CaptureScreenRoute")
//                        {
//                            CaptureImageScreen(
//                                context = thisContext
//                            )
//                        }
//
//                        composable("ScoreScreenRoute/{score}")
//                        {
//                            var score: String = it.arguments?.getString("score") ?: ""
////                            ScoreScreen(
////                                finalScore = score.toInt(),
////                                highScore = highScore,
////                                onHighScoreUpdate = { highScore = it }
////                            )
//                        }
//                    }
//                }
//            }
//        )
//    }
//}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBottomBar(
    navController: NavController,
    //inSettingsScreen: Boolean,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = { TopBar(navController) },
        bottomBar = { BottomBar(navController) },
        content = { content() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController) {
    TopAppBar(
        modifier = Modifier.padding(0.dp),
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        title = { Text("EcoCap") },
        actions = {
            IconButton(
                onClick = { navController.navigate("SettingsScreenRoute") },
                //enabled = !inSettingsScreen
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "Settings",
                    modifier = Modifier
                        .size(300.dp)
                        .padding(0.dp)
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar(navController: NavController) {
    BottomAppBar(
        actions = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = { navController.navigate("HomeScreenRoute") },
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                ) {
                    Icon(Icons.Rounded.Home, contentDescription = "Home", Modifier.size(40.dp))
                }
                IconButton(
                    onClick = { navController.navigate("HistoryScreenRoute") },
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                ) {
                    Icon(Icons.Filled.DateRange, contentDescription = "History", Modifier.size(40.dp))
                }
                IconButton(
                    onClick = { navController.navigate("CaptureScreenRoute") },
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                ) {
                    Icon(Icons.Filled.AddCircle, contentDescription = "Capture", Modifier.size(40.dp))
                }
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                ) {
                    Icon(Icons.Filled.AccountCircle, contentDescription = "Profile", Modifier.size(40.dp))
                }
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                ) {
                    Icon(Icons.Filled.Settings, contentDescription = "Settings", Modifier.size(40.dp))
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.tertiary,
    )
}

@Preview
@Composable
fun DefaultPreview() {
    EcoCapTheme {
//        HomeScreen()
    }
}



