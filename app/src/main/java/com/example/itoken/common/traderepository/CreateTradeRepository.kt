package com.example.itoken.common.traderepository

import com.example.itoken.features.trades.domain.model.Lot

interface CreateTradeRepository {

    suspend fun createTrade(trade: Lot)

}