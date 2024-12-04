package com.example.ecocap.ui.Authentication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ecocap.Data.Database.UserStore
import com.example.ecocap.Data.Repository.UserRepository

class AuthenticationViewModel(val userRepository: UserRepository): ViewModel() {
    var sessionUserId by mutableStateOf<Int?>(null)

    suspend fun register(username: String, password: String){
        //register user to database

        //set sessionId
        val userStore: UserStore = UserStore(name = username, totalPoints = 0)
        userRepository.insertUser(userStore)
        //return sessionId
    }

    fun login(username: String, password: String){
        //login user

        //set sessionId
        //return sessionId
    }
}