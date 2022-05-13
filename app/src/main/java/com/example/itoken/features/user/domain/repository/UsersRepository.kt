package com.example.itoken.features.user.domain.repository

import com.example.itoken.features.user.domain.model.UserModel

interface UsersRepository {

    suspend fun registerUser(user: UserModel): Boolean
    suspend fun addUser(user: UserModel): UserModel
    suspend fun getUser(): UserModel?
    suspend fun signOut()
    suspend fun changeBalance(newBalance: Double?)
    suspend fun changePhoto(newPhoto: String?)

}