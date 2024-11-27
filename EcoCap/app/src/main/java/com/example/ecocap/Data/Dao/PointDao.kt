package com.example.ecocap.Data.Dao

import com.example.ecocap.Data.Database.PointStore
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PointDao {
    @Insert
    suspend fun insert(points: PointStore)

    @Query("SELECT * FROM points WHERE userId = :userId ")
    suspend fun getAllPointsFromUser(userId:Int): List<PointStore>
}