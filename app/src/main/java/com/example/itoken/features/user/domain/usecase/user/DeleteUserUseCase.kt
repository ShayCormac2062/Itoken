package com.example.itoken.features.user.domain.usecase.user

import com.example.itoken.features.user.domain.repository.UsersRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val repository: UsersRepository
) {

    suspend operator fun invoke() =
        repository.signOut()

}