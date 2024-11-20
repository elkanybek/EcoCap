package com.example.ecocap.Data.Repository

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quests")
data class QuestStore (
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String
)