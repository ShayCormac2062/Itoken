package com.example.itoken.features.user.domain.usecase

import com.example.itoken.common.db.model.User
import com.example.itoken.features.user.domain.model.UserModel
import com.example.itoken.features.user.domain.repository.AssetsRepository
import com.example.itoken.features.user.domain.repository.UsersRepository
import com.example.itoken.utils.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val repository: UsersRepository) {

    suspend operator fun invoke(user: UserModel) = repository.addUser(user)
}