package com.example.ecocap.Data.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "streak_table")
data class StreakScore(
    @PrimaryKey val userId: Int,
    val dailyStreak: Int,
    val lastSessionDate: Long
)
