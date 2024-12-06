package com.example.ecocap.Data.Database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.ecocap.Data.Dao.PointDao
import com.example.ecocap.Data.Dao.QuestDao
import com.example.ecocap.Data.Dao.StreakDao
import com.example.ecocap.Data.Dao.UserDao

object DatabaseProvider {
    @Database(entities = [UserStore::class, PointStore::class, QuestStore::class, StreakScore::class], version = 5)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun userDao(): UserDao
        abstract fun pointDao(): PointDao
        abstract fun questDao(): QuestDao
        abstract fun streakDao(): StreakDao

        companion object {
            @Volatile
            private var INSTANCE: AppDatabase? = null

            fun getInstance(context: Context): AppDatabase {
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                    instance
                }
            }
        }
    }
}
