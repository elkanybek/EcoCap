package com.example.ecocap.ui.Screens.Profile

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.LaunchedEffect
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
    var points = 0
    var username by mutableStateOf("DavyDav")
    var password by mutableStateOf("********")

    init {
        viewModelScope.launch {
            var user: UserStore? = userRepository.getUser(sessionId!!)
            if(user != null){
                username = user.name
                password = user.password
            }
        }
    }

    suspend fun update(){
        var user: UserStore? = userRepository.getUser(sessionId!!)
        if(user != null){
            username = user.name
            password = user.password
        }
    }

    fun updateUser(newUsername: String, newPassword: String, confirmPassword: String): Boolean{
        if(newPassword != confirmPassword){
            return false
        }

        viewModelScope.launch {
            points = userRepository.getUserPoints(sessionId!!)
            userRepository.updateUser(UserStore(id = sessionId!!, name = newUsername, password = newPassword, totalPoints = points))

            var user: UserStore? = userRepository.getUser(sessionId!!)
            username = user!!.name
            password = user.password

        }
        return true
    }
}