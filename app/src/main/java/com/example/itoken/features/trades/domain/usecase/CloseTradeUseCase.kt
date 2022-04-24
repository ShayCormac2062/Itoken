package com.example.itoken.features.trades.domain.usecase

import com.example.itoken.features.trades.domain.repository.TransactionRepository
import javax.inject.Inject

class CloseTradeUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(tradeId: String?) = repository.closeTrade(tradeId)

}