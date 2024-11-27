package com.example.ecocap.Data.Repository

import com.example.ecocap.Data.Dao.UserDao
import com.example.ecocap.Data.Database.PointStore
import com.example.ecocap.Data.Database.UserStore

class UserRepository(private val userDao: UserDao) {
    suspend fun insertUser(user: UserStore) {
        userDao.insert(user)
    }

    suspend fun getUserPoints(userId: Int){
        userDao.getPointsFromId(userId)
    }
}