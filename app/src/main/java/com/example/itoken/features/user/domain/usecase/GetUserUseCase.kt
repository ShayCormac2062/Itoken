package com.example.itoken.features.user.domain.usecase

import com.example.itoken.features.user.domain.repository.UsersRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: UsersRepository
) {
    suspend operator fun invoke() = repository.getUser()
}