package com.example.awpsubmission3.data.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteUser::class],
    version = 1
)
abstract class UserDatabase : RoomDatabase() {

    abstract fun favoriteUserDao() : FavoriteUserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase = INSTANCE ?: synchronized(this) {
            Room.databaseBuilder(
                context.applicationContext,
                UserDatabase::class.java,
                "user_dabatase"
            ).build().apply {
                INSTANCE = this
            }
        }
    }
}