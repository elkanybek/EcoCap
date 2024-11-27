package com.example.ecocap.Data.Dao

import com.example.ecocap.Data.Database.QuestStore
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuestDao {
    @Insert
    suspend fun insert(user: QuestStore)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<QuestStore>)

    @Query("SELECT * FROM quests")
    suspend fun getAllQuests(): List<QuestStore>

    @Query("SELECT * FROM quests ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomQuest(): QuestStore
}