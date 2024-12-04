package com.example.ecocap.Data.Dao

import com.example.ecocap.Data.Database.UserStore
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ecocap.Data.Database.PointStore

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: UserStore)

    @Query("SELECT id FROM users WHERE password = :password AND name= :name ")
    suspend fun getUserId(name: String, password: String): Int

    @Query("SELECT totalPoints FROM users WHERE id = :userId ")
    suspend fun getPointsFromId(userId:Int): Int

    @Query("UPDATE users SET totalPoints = :newPoints WHERE id = :userId")
    suspend fun updateTotalPoints(userId:Int, newPoints: Int)

    @Update
    suspend fun update(user: UserStore)
}