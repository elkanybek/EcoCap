//package com.example.ecocap.Data.Dao
//
//import PointStore
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.Query
//
//@Dao
//interface PointDao {
//    @Insert
//    suspend fun insert(user: PointStore)
//
//    @Query("SELECT * FROM points")
//    suspend fun getAllPoints(): List<PointStore>
//}