package com.example.ecocap.Data.Repository

import com.example.ecocap.Data.Database.PointStore
import com.example.ecocap.Data.Dao.PointDao


class PointRepository(private val pointDao: PointDao) {
    suspend fun insertPoints(points: PointStore) {
        pointDao.insert(points)
    }

    suspend fun getAllPoints(userId: Int): List<PointStore> {
        return pointDao.getAllPointsFromUser(userId)
    }
}