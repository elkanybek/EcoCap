package com.example.ecocap.Data.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserStore (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val password: String,
    val totalPoints: Int
)

