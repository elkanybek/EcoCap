package com.example.ecocap.ui.Screens.Profile

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecocap.Data.Database.PointStore
import com.example.ecocap.Data.Database.QuestStore
import com.example.ecocap.Data.Database.UserStore
import com.example.ecocap.Data.Repository.PointRepository
import com.example.ecocap.Data.Repository.UserRepository
import com.example.ecocap.ui.Camera.CaptureImageViewModel
import com.example.ecocap.ui.Screens.Home.HomeViewModel
import com.google.mlkit.vision.label.ImageLabel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.InputStream

class ProfileViewModel(private val userRepository: UserRepository): ViewModel() {
    var sessionId by mutableStateOf<Int?>(1)
    var name by mutableStateOf("")
    var password by mutableStateOf("")

}