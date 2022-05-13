package com.example.itoken.features.user.domain.usecase.user

import com.example.itoken.features.user.domain.model.UserModel
import com.example.itoken.features.user.domain.repository.UsersRepository
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val repository: UsersRepository) {

    suspend operator fun invoke(user: UserModel): UserModel {
        return repository.addUser(user)
    }

}