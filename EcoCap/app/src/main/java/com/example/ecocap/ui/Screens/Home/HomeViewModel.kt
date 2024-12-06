package com.example.ecocap.ui.Screens.Home

import QuestRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ecocap.Data.Database.QuestStore

class HomeViewModel(private val questRepository: QuestRepository): ViewModel() {
    val questsAmount = 3
    var sessionId: Int = 1

    var quests: List<QuestStore> = mutableListOf()

//    init{
//        viewModelScope.launch {
//            questRepository.insertQuests(quests)
//        }
//    }

    suspend fun getQuest(): QuestStore {
        return questRepository.getRandomQuest()
    }

    suspend fun getQuests(): List<QuestStore>{
        if(quests.isEmpty()){
            quests = listOf(
                questRepository.getRandomQuest(),
                questRepository.getRandomQuest(),
                questRepository.getRandomQuest(),
                questRepository.getRandomQuest(),
                questRepository.getRandomQuest(),
                questRepository.getRandomQuest(),
                questRepository.getRandomQuest(),
                questRepository.getRandomQuest(),
                questRepository.getRandomQuest(),
                questRepository.getRandomQuest(),
                questRepository.getRandomQuest(),
                questRepository.getRandomQuest(),
                questRepository.getRandomQuest(),
                questRepository.getRandomQuest(),
                questRepository.getRandomQuest(),
                questRepository.getRandomQuest(),
                questRepository.getRandomQuest(),
                questRepository.getRandomQuest(),
                questRepository.getRandomQuest(),
                questRepository.getRandomQuest(),
                questRepository.getRandomQuest(),
                questRepository.getRandomQuest(),
            )
        }
        return quests
    }

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

    var dailyStreak by mutableStateOf(0)
}