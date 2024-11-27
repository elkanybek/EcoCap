package com.example.ecocap.Data.Dao

import com.example.ecocap.Data.Database.UserStore
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: UserStore)
}