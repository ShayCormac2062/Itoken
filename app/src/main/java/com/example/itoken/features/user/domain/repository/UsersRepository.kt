package com.example.itoken.features.user.domain.repository

import com.example.itoken.features.user.domain.model.UserModel

interface UsersRepository {

    suspend fun addUser(user: UserModel)
    suspend fun signOut()
    suspend fun changeBalance(newBalance: Double?)

}