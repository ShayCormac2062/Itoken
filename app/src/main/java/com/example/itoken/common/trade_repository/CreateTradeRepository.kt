package com.example.itoken.common.trade_repository

import com.example.itoken.features.trades.domain.model.Lot

interface CreateTradeRepository {

    suspend fun createTrade(trade: Lot)

}