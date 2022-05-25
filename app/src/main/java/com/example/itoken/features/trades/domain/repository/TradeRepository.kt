package com.example.itoken.features.trades.domain.repository

import com.example.itoken.features.trades.domain.model.TradeModel

interface TradeRepository {

    suspend fun getAllTrades(): List<TradeModel>

}
