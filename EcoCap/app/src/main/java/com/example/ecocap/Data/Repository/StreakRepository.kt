package com.example.ecocap.Data.Repository

import com.example.ecocap.Data.Dao.StreakDao
import com.example.ecocap.Data.Database.StreakScore

class StreakRepository(private val streakDao: StreakDao) {

    suspend fun insertStreak(streak: StreakScore) {
        streakDao.insertStreak(streak)
    }

    suspend fun getStreak(userId: Int): StreakScore? {
        return streakDao.getStreak(userId)
    }

    suspend fun updateStreak(userId: Int, streak: Int, lastSessionDate: Long) {
        streakDao.updateStreak(userId, streak, lastSessionDate)
    }
}
