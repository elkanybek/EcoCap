package com.example.ecocap.ui.Screens

import androidx.lifecycle.ViewModel
import com.example.ecocap.Data.Database.PointStore
import com.example.ecocap.Data.Database.QuestStore
import com.example.ecocap.Data.Repository.PointRepository

class HistoryViewModel(val pointRepository: PointRepository): ViewModel() {
    var sessionId: Int = 1

    val animals = listOf(
        "Frog",
        "Tiger",
        "Elephant",
        "Penguin",
        "Panda",
        "Koala",
        "Giraffe",
        "Lion",
        "Zebra",
        "Kangaroo",
        "Polar Bear"
    )

    suspend fun getQuests(userId: Int): List<PointStore>{
        return pointRepository.getAllPoints(userId)
    }
}