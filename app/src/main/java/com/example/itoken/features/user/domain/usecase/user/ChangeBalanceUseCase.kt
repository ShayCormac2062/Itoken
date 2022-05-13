package com.example.itoken.features.user.domain.usecase.user

import com.example.itoken.features.user.domain.repository.UsersRepository
import com.example.itoken.utils.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChangeBalanceUseCase @Inject constructor(
    private val repository: UsersRepository
) {

    suspend operator fun invoke(newBalance: Double?) = repository.changeBalance(newBalance)

}