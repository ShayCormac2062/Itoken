package com.example.itoken.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.itoken.common.db.model.User
import com.example.itoken.features.user.data.db.dao.UsersDao

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = true
)
abstract class UserDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDao
}