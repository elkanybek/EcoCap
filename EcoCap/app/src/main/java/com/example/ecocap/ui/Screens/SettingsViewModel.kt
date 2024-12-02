package com.example.ecocap.ui.Screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecocap.Data.Database.PointStore
import com.example.ecocap.Data.Database.QuestStore
import com.example.ecocap.Data.Repository.PointRepository
import kotlinx.coroutines.launch

class SettingsViewModel(): ViewModel() {
    var sessionId: Int = 1
    var darkIsEnabled by mutableStateOf(false)



    fun toggleDarkMode(Boolean: Boolean){
//        darkIsEnabled = !darkIsEnabled
        darkIsEnabled = Boolean
    }


}