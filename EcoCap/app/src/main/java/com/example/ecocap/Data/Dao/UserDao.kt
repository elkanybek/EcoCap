package com.example.ecocap.Data.Dao

import com.example.ecocap.Data.Database.UserStore
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ecocap.Data.Database.PointStore

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: UserStore)

    @Query("SELECT totalPoints FROM users WHERE id = :userId ")
    suspend fun getPointsFromId(userId:Int): Int
}