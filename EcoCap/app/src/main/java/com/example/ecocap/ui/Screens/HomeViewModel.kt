package com.example.ecocap.ui.Screens

import QuestRepository
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecocap.Data.Database.DatabaseProvider
import com.example.ecocap.Data.Database.QuestStore
import com.example.ecocap.Data.quests
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId


import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecocap.Data.Repository.PointRepository
import com.example.ecocap.Data.Repository.StreakRepository
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date

//class HomeViewModel(private val questRepository: QuestRepository): ViewModel() {
//    val questsAmount = 3
//    var sessionId: Int = 1
//    var dailyStreak by mutableStateOf(0)
//    var quests: List<QuestStore> = mutableListOf()
//
////    init{
////        viewModelScope.launch {
////            questRepository.insertQuests(quests)
////        }
////    }
//
//    suspend fun getQuest(): QuestStore {
//        return questRepository.getRandomQuest()
//    }
//
//    suspend fun getQuests(): List<QuestStore>{
//        if(quests.isEmpty()){
//            quests = listOf(
//                questRepository.getRandomQuest(),
//                questRepository.getRandomQuest(),
//                questRepository.getRandomQuest(),
//                questRepository.getRandomQuest(),
//                questRepository.getRandomQuest(),
//                questRepository.getRandomQuest(),
//                questRepository.getRandomQuest(),
//                questRepository.getRandomQuest(),
//                questRepository.getRandomQuest(),
//                questRepository.getRandomQuest(),
//                questRepository.getRandomQuest(),
//                questRepository.getRandomQuest(),
//                questRepository.getRandomQuest(),
//                questRepository.getRandomQuest(),
//                questRepository.getRandomQuest(),
//                questRepository.getRandomQuest(),
//                questRepository.getRandomQuest(),
//                questRepository.getRandomQuest(),
//                questRepository.getRandomQuest(),
//                questRepository.getRandomQuest(),
//                questRepository.getRandomQuest(),
//                questRepository.getRandomQuest(),
//            )
//        }
//        return quests
//    }
//
//    val animals = listOf(
//        "Frog",
//        "Tiger",
//        "Elephant",
//        "Penguin",
//        "Panda",
//        "Koala",
//        "Giraffe",
//        "Lion",
//        "Zebra",
//        "Kangaroo",
//        "Polar Bear"
//    )
//
//}

class HomeViewModel(
    private val streakRepository: StreakRepository,
    //private val userId: Int = 1,
    private val questRepository: QuestRepository
) : ViewModel() {
    val userId: Int = 1
    var dailyStreak by mutableStateOf(0)
    private var lastSessionDate: Long = 0
    var quests: List<QuestStore> = mutableListOf()

    init {
        // Load the last streak and session date from the database
        loadStreakData()
    }

    private fun loadStreakData() {
        viewModelScope.launch {
            val streakScore = streakRepository.getStreak(userId)
            if (streakScore != null) {
                dailyStreak = streakScore.dailyStreak
                lastSessionDate = streakScore.lastSessionDate
            }
        }
    }

    private fun saveStreakData() {
        viewModelScope.launch {
            streakRepository.updateStreak(userId, dailyStreak, Date().time)
        }
    }

//    fun checkStreak() {
////        val today = System.currentTimeMillis()
//
//        val today = Date().time
//
//        // Timestamps to calendar days
//        val lastSessionDay = lastSessionDate / (24 * 60 * 60 * 1000)
//        val currentDay = today / (24 * 60 * 60 * 1000)
//
//        if (currentDay == lastSessionDay) {
//            return
//        } else if ((currentDay - lastSessionDay).toInt() <= 1) {
//            dailyStreak++
//        } else {
//            dailyStreak = 1
//        }
//
//        lastSessionDate = today
//        saveStreakData()
//    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun checkStreak() {
        val today = LocalDate.now(ZoneId.systemDefault())
        val lastSessionDay = Instant.ofEpochMilli(lastSessionDate)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        if (today == lastSessionDay) {
            return
        }

        if (today.minusDays(1) == lastSessionDay) {
            dailyStreak++
        } else {
            dailyStreak = 1
        }

        lastSessionDate = today.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        saveStreakData()
    }


    suspend fun getQuest(): QuestStore {
        return questRepository.getRandomQuest()
    }

    suspend fun getQuests(): List<QuestStore> {
        if (quests.isEmpty()) {
            quests = listOf(
                questRepository.getRandomQuest(),
                questRepository.getRandomQuest(),
                questRepository.getRandomQuest()
            )
        }
        return quests
    }
}
