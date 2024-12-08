package com.example.ecocap.ui.Screens.Home

import QuestRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ecocap.Data.Database.QuestStore

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecocap.Data.Repository.PointRepository
import com.example.ecocap.Data.Repository.StreakRepository
import kotlinx.coroutines.launch
import java.util.Date

class HomeViewModel(
    private val streakRepository: StreakRepository,
    //private val userId: Int = 1,
    private val questRepository: QuestRepository
) : ViewModel() {
    var dailyStreak by mutableStateOf(0)
    private var lastSessionDate: Long = 0
    var sessionId by mutableStateOf<Int?>(1)
    var quests: MutableList<QuestStore> = mutableListOf()

    init {
        // Load the last streak and session date from the database
        loadStreakData()
    }

    private fun loadStreakData() {
        viewModelScope.launch {
            val streakScore = streakRepository.getStreak(sessionId!!)
            if (streakScore != null) {
                dailyStreak = streakScore.dailyStreak
                lastSessionDate = streakScore.lastSessionDate
            }
        }
    }

    private fun saveStreakData() {
        viewModelScope.launch {
            streakRepository.updateStreak(sessionId!!, dailyStreak, Date().time)
        }
    }

    fun checkStreak() {
//        val today = System.currentTimeMillis()

        val today = Date().time

        // Timestamps to calendar days
        val lastSessionDay = lastSessionDate / (24 * 60 * 60 * 1000)
        val currentDay = today / (24 * 60 * 60 * 1000)

        if (currentDay == lastSessionDay) {
            return
        } else if ((currentDay - lastSessionDay).toInt() <= 1) {
            dailyStreak++
        } else {
            dailyStreak = 1
        }

        lastSessionDate = today
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
            ).toMutableList()
        }
        return quests
    }
}
