package com.example.itoken.features.trades.domain.usecase

import com.example.itoken.features.trades.domain.model.Lot
import com.example.itoken.common.trade_repository.CreateTradeRepository
import javax.inject.Inject

class CreateTradeUseCase @Inject constructor(
    private val repository: CreateTradeRepository
) {

    suspend operator fun invoke(lot: Lot) = repository.createTrade(lot)

}