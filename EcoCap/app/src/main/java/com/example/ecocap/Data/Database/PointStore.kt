package com.example.ecocap.Data.Database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "points",
    foreignKeys = [
        ForeignKey(
            entity = UserStore::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE //if user deleted, delete points too
        )
    ]
)
data class PointStore (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val questName: String,
    val image: ByteArray,
    val streakMultiplier: Double,
    val scoreGained: Int
)
