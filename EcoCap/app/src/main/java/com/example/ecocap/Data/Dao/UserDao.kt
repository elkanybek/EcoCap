//package com.example.ecocap.Data.Dao
//
//import UserStore
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.Query
//
//@Dao
//interface UserDao {
//    @Insert
//    suspend fun insert(user: UserStore)
//
//    @Query("SELECT * FROM users")
//    suspend fun getAllUsers(): List<UserStore>
//}