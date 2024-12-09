package com.example.ecocap

import QuestRepository
import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
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
import com.example.ecocap.ui.Camera.CaptureImageScreen
import com.example.ecocap.ui.Screens.History.HistoryScreen
import com.example.ecocap.ui.Screens.Home.HomeScreen
import com.example.ecocap.ui.Screens.SettingsScreen
import com.example.ecocap.ui.theme.EcoCapTheme
import androidx.activity.viewModels
import androidx.compose.material3.DatePicker
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ecocap.Data.Database.DatabaseProvider
import com.example.ecocap.Data.Database.PointStore
import com.example.ecocap.Data.Database.QuestStore
import com.example.ecocap.Data.Database.StreakScore
import com.example.ecocap.Data.Database.UserStore
import com.example.ecocap.Data.Repository.PointRepository
import com.example.ecocap.Data.Repository.StreakRepository
import com.example.ecocap.Data.quests
import com.example.ecocap.ui.Camera.CaptureImageViewModel
import com.example.ecocap.ui.Screens.History.HistoryViewModel
import com.example.ecocap.ui.Screens.Home.HomeViewModel
import com.example.ecocap.ui.Screens.ResultScreen
import com.example.ecocap.ui.Screens.ResultViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.ecocap.Data.Repository.UserRepository
import com.example.ecocap.ui.Authentication.AuthenticationViewModel
import com.example.ecocap.ui.Screens.Login.LoginScreen
import com.example.ecocap.ui.Screens.Login.RegisterScreen
import com.example.ecocap.ui.Screens.Profile.ProfileScreen
import com.example.ecocap.ui.Screens.SettingsViewModel
import java.util.Date


val LocalNavController = compositionLocalOf<NavController> { error("No NavController found!") }



class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val db = DatabaseProvider.AppDatabase.getInstance(applicationContext)
            val questRepository = QuestRepository(db.questDao())
            val pointRepository = PointRepository(db.pointDao())
            val userRepository = UserRepository(db.userDao())
            val streakRepository = StreakRepository(db.streakDao())

            LaunchedEffect(Unit) {
                launch(Dispatchers.IO) {
                    questRepository.insertQuests(quests)
                }
            }
//            val user: UserStore = UserStore(
//                id = 1,
//                name = "Bob",
//                password = "password",
//                totalPoints = 0
//            )
//            LaunchedEffect(Unit) {
//                launch(Dispatchers.IO) {
//                    userRepository.insertUser(user)
//                }
//            }
//            val streak: StreakScore = StreakScore(
//                userId = 1,
//                dailyStreak = 0,
//                lastSessionDate = (Date().time - (24*60*60*1000))
//            )
//            LaunchedEffect(Unit) {
//                launch(Dispatchers.IO) {
//                    streakRepository.insertStreak(streak)
//                }
//            }

            val homeViewModel: HomeViewModel by viewModels{
                HomeViewModelFactory(questRepository, streakRepository)
            }
            val historyViewModel: HistoryViewModel by viewModels{
                HistoryViewModelFactory(pointRepository)
            }
            val resultViewModel: ResultViewModel by viewModels{
                ResultViewModelFactory(pointRepository, userRepository, homeViewModel)
            }

            val settingsViewModel: SettingsViewModel by viewModels{
                SettingsViewModelFactory()
            }

//            var isDarkTheme by remember { mutableStateOf(false) }

            val captureImageViewModel: CaptureImageViewModel by viewModels()

            val authenticationViewModel: AuthenticationViewModel by viewModels{
                AuthenticationViewModelFactory(userRepository)
            }

            homeViewModel.sessionId = authenticationViewModel.userId
            captureImageViewModel.sessionId = authenticationViewModel.userId
            historyViewModel.sessionId = authenticationViewModel.userId
            resultViewModel.sessionId = authenticationViewModel.userId
            settingsViewModel.sessionId = authenticationViewModel.userId



            EcoCapTheme(darkTheme = settingsViewModel.darkIsEnabled) {
                Router(this, captureImageViewModel, homeViewModel, historyViewModel, resultViewModel, settingsViewModel, authenticationViewModel)
            }
        }
    }
}

/**
 * Router for the Quiz App.
 * Handles navigation between screens.
 *
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Router(
    context: Context,
    captureImageViewModel: CaptureImageViewModel,
    homeViewModel: HomeViewModel,
    historyViewModel: HistoryViewModel,
    resultViewModel: ResultViewModel,
    settingsViewModel: SettingsViewModel,
    authenticationViewModel: AuthenticationViewModel
) {
    val navController = rememberNavController()
    var canNavigateBack by rememberSaveable { mutableStateOf(false) }
    var inSettingsScreen by rememberSaveable { mutableStateOf(false) }

    var quests by remember { mutableStateOf<List<QuestStore>>(emptyList()) }

    LaunchedEffect(Unit) {
        quests = homeViewModel.getQuests()
    }

    TopBottomBar(navController, resultViewModel.totalPoints, userId = authenticationViewModel.userId) {
        CompositionLocalProvider(LocalNavController provides navController) {
            NavHost(navController = navController, startDestination = "LoginScreenRoute",  enterTransition = { slideInHorizontally { length -> length } }, exitTransition = { slideOutHorizontally { length -> -length } }) {

                //NavBar
                composable("HomeScreenRoute") {
                    HomeScreen(
                        dailyStreak = homeViewModel.dailyStreak,
                        quests
                    )
                }
                composable("HistoryScreenRoute") {
                    HistoryScreen(
                        userId = historyViewModel.sessionId,
                        getHistory = {userId: Int -> historyViewModel.getPoints(userId)}
                    )
                }
                composable("CaptureScreenRoute") {
                    CaptureImageScreen(
                        context = context,
                        selectedImageUri = captureImageViewModel.selectedImageUri,
                        imageLabel = captureImageViewModel.topLabels,
                        setImage = { context: Context, image: Uri? ->
                            captureImageViewModel.processImage(context, image)
                        },

                        checkResult = {
                            navController.navigate("ResultScreenRoute")
                        }
                    )
                }
                composable("ProfileScreenRoute") {
                    ProfileScreen(
                        onLogout = {
                            authenticationViewModel.userId = null
                            navController.navigate("LoginScreenRoute")
                        }
                    )

                }
                composable("SettingsScreenRoute") {
                    SettingsScreen(
                        darkIsEnabled = settingsViewModel.darkIsEnabled,
                        onThemeToggle = {
                            settingsViewModel.toggleDarkMode(it)
                        },
                    )
                }



                composable("ScoreScreenRoute/{score}") {
                    val score: String = it.arguments?.getString("score") ?: ""
                }
                composable("ResultScreenRoute"){
                    var result by mutableStateOf<Boolean>(false)
                    LaunchedEffect(result) {
                        result = resultViewModel.checkResult(
                            homeViewModel.quests,
                            captureImageViewModel.inputLabelList,
                            captureImageViewModel.selectedImageUri,
                            context = context
                        )
                    }

                    ResultScreen(
                        image = resultViewModel.imageBytes,
                        result = resultViewModel.result,
                        pointsGained = resultViewModel.pointsGained
                    )
                }


                composable("LoginScreenRoute") {
                    LoginScreen(
                        onLogin = { username: String, password: String ->
                            val userId: Int? = authenticationViewModel.login(username, password)
                            if(userId != 0){
                                navController.navigate("HomeScreenRoute")
                            }
                        },
                        onRegister = { navController.navigate("RegisterScreenRoute") }
                    )
                }

                composable ("RegisterScreenRoute") {
                    RegisterScreen(
                        onRegister = { username: String, password: String, confirmPassword: String ->
                            authenticationViewModel.register(username, password, confirmPassword)
                            navController.navigate("HomeScreenRoute")
                                     },
                        onLogin = { navController.navigate("LoginScreenRoute") }
                    )
                }
            }
        }
    }


}

class HomeViewModelFactory(private val questRepository: QuestRepository, private val streakRepository: StreakRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(streakRepository, questRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class HistoryViewModelFactory(private val pointRepository: PointRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryViewModel(pointRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class ResultViewModelFactory(private val pointRepository: PointRepository, private val userRepository: UserRepository, private val homeViewModel: HomeViewModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ResultViewModel(pointRepository, userRepository, homeViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class SettingsViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class AuthenticationViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AuthenticationViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return AuthenticationViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}






@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBottomBar(
    navController: NavController,
    //inSettingsScreen: Boolean,
    score: Int,
    userId: Int?,
    content: @Composable () -> Unit,
) {
    Scaffold(
        topBar = { TopBar(navController, score) },
        bottomBar = { BottomBar(navController, userId) },
        content = { content() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController, score: Int) {
    TopAppBar(
        modifier = Modifier.padding(0.dp),
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        title = { Text("EcoCap") },
        actions = {
            Text(
                text = "Points: $score",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .align(Alignment.CenterVertically)
            )
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
fun BottomBar(navController: NavController, userId: Int?) {
    if(userId != 0 && userId != null){
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
                        onClick = { navController.navigate("ProfileScreenRoute") },
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                    ) {
                        Icon(Icons.Filled.AccountCircle, contentDescription = "Profile", Modifier.size(40.dp))
                    }
                    IconButton(
                        onClick = { navController.navigate("SettingsScreenRoute") },
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
}

@Preview
@Composable
fun DefaultPreview() {
    EcoCapTheme {
//        HomeScreen()
    }
}



