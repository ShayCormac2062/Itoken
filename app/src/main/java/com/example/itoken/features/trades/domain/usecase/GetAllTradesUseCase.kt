package com.example.itoken.features.trades.domain.usecase

import com.example.itoken.features.trades.domain.repository.TradeRepository
import javax.inject.Inject

class GetAllTradesUseCase @Inject constructor(
    private val repository: TradeRepository
) {

    suspend operator fun invoke() = repository.getAllTrades()

}