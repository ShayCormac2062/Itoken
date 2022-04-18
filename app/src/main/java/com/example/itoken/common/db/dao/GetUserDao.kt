package com.example.itoken.common.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.itoken.common.db.model.User

@Dao
interface GetUserDao {
    @Query("SELECT * from users")
    suspend fun getUser(): User?
}