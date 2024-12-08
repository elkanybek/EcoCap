package com.example.ecocap.ui.Screens.History

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ecocap.Data.Database.PointStore
import com.example.ecocap.Data.Database.QuestStore
import com.example.ecocap.Data.Repository.PointRepository

class HistoryViewModel(private val pointRepository: PointRepository): ViewModel() {
    var sessionId by mutableStateOf<Int?>(null)

    suspend fun getPoints(userId: Int): List<PointStore> {
        return pointRepository.getAllPoints(userId)
    }
}