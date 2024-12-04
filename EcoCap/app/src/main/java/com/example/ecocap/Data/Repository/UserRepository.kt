package com.example.ecocap.Data.Repository

import androidx.compose.ui.text.input.PasswordVisualTransformation
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

    suspend fun getUserId(name: String, password: String){
        userDao.getUserId(name, password)
    }

    suspend fun updateUser(user: UserStore){
        userDao.update(user)
    }
}