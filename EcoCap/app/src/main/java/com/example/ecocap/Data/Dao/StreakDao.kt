package com.example.ecocap.Data.Dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ecocap.Data.Database.StreakScore

@Dao
interface StreakDao {
    @Insert
    suspend fun insertStreak(streak: StreakScore)

    @Query("SELECT * FROM streak_table WHERE userId = :userId LIMIT 1")
    suspend fun getStreak(userId: Int): StreakScore?

    @Query("UPDATE streak_table SET dailyStreak = :streak, lastSessionDate = :lastSessionDate WHERE userId = :userId")
    suspend fun updateStreak(userId: Int, streak: Int, lastSessionDate: Long)
}
