package com.example.ecocap.ui.Authentication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ecocap.Data.Database.UserStore
import com.example.ecocap.Data.Repository.UserRepository

class AuthenticationViewModel(val userRepository: UserRepository): ViewModel() {
    var userId by mutableStateOf<Int?>(0)

    suspend fun register(username: String, password: String, confirmPassword: String): Int?{
        if(password != confirmPassword || userRepository.usernameExists(username)){
            return null
        }
        //register user to database
        val userStore: UserStore = UserStore(
            name = username,
            password = password,
            totalPoints = 0)

        userRepository.insertUser(userStore)

        userId = userRepository.getUserId(name = username, password = password)

        return userId
    }


    suspend fun login(username: String, password: String): Int?{
        userId = userRepository.getUserId(name = username, password = password)

        return userId
    }
}