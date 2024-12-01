package com.example.ecocap.ui.Screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ecocap.Data.Database.PointStore
import com.example.ecocap.Data.Database.QuestStore
import com.example.ecocap.Data.Repository.PointRepository
import com.google.mlkit.vision.label.ImageLabel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ResultViewModel(private val pointRepository: PointRepository): ViewModel() {
    var sessionId: Int = 1
    var result by mutableStateOf(false)
    var pointsGained by mutableStateOf(0)

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

    suspend fun checkResult(quests: List<QuestStore>, labels: List<ImageLabel>): Boolean{
        pointsGained = 0
        result = false
        for(quest in quests){
            for(label in labels){
                if(quest.name == label.text && label.confidence > 0.2){
//                if(true){ // for testing
                    result = true
                    pointsGained = 200

                    val pointStore: PointStore = PointStore(
                        userId = sessionId,
                        questName = quest.name,
                        image = "empty",
                        streakMultiplier = 1.0,
                        scoreGained = 200
                    )
                    pointRepository.insertPoints(pointStore)
                    return true
                }
            }
        }
        return false
    }
}