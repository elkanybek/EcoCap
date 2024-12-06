package com.example.ecocap.ui.Screens.History

import androidx.lifecycle.ViewModel
import com.example.ecocap.Data.Database.PointStore
import com.example.ecocap.Data.Repository.PointRepository

class HistoryViewModel(val pointRepository: PointRepository): ViewModel() {
    var sessionId: Int = 1

    suspend fun getPoints(userId: Int): List<PointStore> {
        return pointRepository.getAllPoints(userId)
    }
}