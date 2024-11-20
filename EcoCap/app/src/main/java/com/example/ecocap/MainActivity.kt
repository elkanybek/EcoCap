package com.example.ecocap

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.ecocap.ui.Camera.CaptureImageScreen
import androidx.activity.ComponentActivity
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.twotone.AddCircle
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ecocap.ui.Screens.HomeScreen
import com.example.ecocap.ui.theme.EcoCapTheme

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

//
//            val context = this
            var isDarkTheme by remember { mutableStateOf(false) }
            EcoCapTheme(darkTheme = isDarkTheme) {
                Router(this)
            }
        }
    }
}

/**
 * Router for the Quiz App.
 * Handles navigation between screens.
 *
 */
@SuppressLint("UnrememberedMutableState", "UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Router(
    context: Context
) {

    val thisContext = context;
    val navController = rememberNavController()
    var canNavigateBack by rememberSaveable() { mutableStateOf(false) }
    var inSettingsScreen by rememberSaveable() { mutableStateOf(false) }

    Surface(
        color = Color(0xFFBA1A1A),
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier.padding(0.dp),
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary       // Sets action icon color
                    ),
                    title = {
//                        Row {
//                            val image = painterResource(R.drawable.logo)
//                            Image(
//                                painter = image,
//                                contentDescription = "EcoCap",
//                                modifier = Modifier
//                                    .size(40.dp)
//                                    .padding(8.dp)
//                            )
                            Text( "EcoCap")
//                        }
                    },
                    actions = {
                        IconButton(
                            onClick = { navController.navigate("SettingsScreenRoute")},
                            enabled = !inSettingsScreen
                        ) {
                            Image(
                                painter = painterResource(R.drawable.logo),
                                contentDescription = "Settings",
                                modifier = Modifier.size(300.dp).padding(0.dp)
                            )
                        }

                    },


                )
            },
            bottomBar = {
                BottomAppBar(
                    actions = {
                        Row (
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ){
                            //1
                            IconButton(
                                onClick = { navController.navigate("HomeScreenRoute")},
//                                enabled = canNavigateBack,
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                            ) {
                                Icon(Icons.Rounded.Home, contentDescription = "Home", Modifier.size(40.dp))
                            }

                            //2
                            IconButton(
                                onClick = { navController.navigate("CaptureScreenRoute")},
//                                enabled = canNavigateBack,
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                            ) {
                                Icon(Icons.Filled.DateRange, contentDescription = "History", Modifier.size(40.dp))
                            }

                            //3
                            IconButton(
                                onClick = { navController.navigate("CaptureScreenRoute")},
//                                enabled = canNavigateBack,
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                            ) {
                                Icon(Icons.Filled.AddCircle, contentDescription = "Caputre", Modifier.size(40.dp))
                            }

                            //4
                            IconButton(
                                onClick = { navController.popBackStack(); inSettingsScreen = false },
//                                enabled = canNavigateBack,
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                            ) {
                                Icon(Icons.Filled.AccountCircle, contentDescription = "Profile", Modifier.size(40.dp))
                            }

                            //5
                            IconButton(
                                onClick = { navController.popBackStack(); inSettingsScreen = false },
//                                enabled = canNavigateBack,
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
            },
            content = {
                CompositionLocalProvider(LocalNavController provides navController) {
                    NavHost(navController = navController, startDestination = "HomeScreenRoute",  enterTransition = { slideInHorizontally { length -> length } }, exitTransition = { slideOutHorizontally { length -> -length } }) {

                        composable("HomeScreenRoute")
                        {
                            HomeScreen()
                        }

                        composable("HistoryScreenRoute")
                        {
//                            canNavigateBack = navController.previousBackStackEntry != null
//                            SettingsScreen(
//                                onThemeChange = onThemeChange,
//                                isInSettings = { inSettingsScreen = it }
//                            )
                        }

                        composable("CaptureScreenRoute")
                        {
                            CaptureImageScreen(
                                context = thisContext
                            )
                        }

                        composable("ScoreScreenRoute/{score}")
                        {
                            var score: String = it.arguments?.getString("score") ?: ""
//                            ScoreScreen(
//                                finalScore = score.toInt(),
//                                highScore = highScore,
//                                onHighScoreUpdate = { highScore = it }
//                            )
                        }
                    }
                }
            }
        )
    }
}


@Preview
@Composable
fun DefaultPreview() {
    EcoCapTheme {
        HomeScreen()
    }
}



