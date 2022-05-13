package com.example.itoken.features.user.data.db.dao

import androidx.room.*
import com.example.itoken.common.db.model.User

@Dao
interface UsersDao {

    @Insert
    suspend fun add(user: User)

    @Query("DELETE from users")
    suspend fun signOut()

    @Query("UPDATE users SET balance=:newBalance")
    suspend fun changeBalance(newBalance: Double?)

    @Query("UPDATE users SET image_url=:imageUrl")
    suspend fun changePhoto(imageUrl: String?)

}