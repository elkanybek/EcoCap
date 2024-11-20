package com.example.ecocap.Data.Dao

import com.example.ecocap.Data.Repository.UserStore
import com.example.ecocap.Data.Repository.PointStore
import com.example.ecocap.Data.Repository.QuestStore
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PointDao {
    @Insert
    suspend fun insert(user: PointStore)

    @Query("SELECT * FROM points")
    suspend fun getAllPoints(): List<PointStore>
}