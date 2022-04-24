package com.example.itoken.features.trades.domain.usecase

import com.example.itoken.features.trades.domain.model.Lot
import com.example.itoken.features.trades.domain.repository.TransactionRepository
import javax.inject.Inject

class SendTokenToUserUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(userId: String?, trade: Lot) = repository.sendTokenToUser(userId, trade)

}