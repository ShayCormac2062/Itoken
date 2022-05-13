package com.example.itoken.features.user.domain.usecase.user

import com.example.itoken.features.user.domain.model.UserModel
import com.example.itoken.features.user.domain.repository.UsersRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: UsersRepository
) {
    suspend operator fun invoke(user: UserModel) = repository.registerUser(user)
}