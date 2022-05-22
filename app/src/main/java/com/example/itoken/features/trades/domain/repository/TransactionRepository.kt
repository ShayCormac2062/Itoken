package com.example.itoken.features.trades.domain.repository

import com.example.itoken.features.trades.domain.model.Auctioneer
import com.example.itoken.features.trades.domain.model.Lot

interface TransactionRepository {

    suspend fun sendTokenToUser(userId: String?, trade: Lot)
    suspend fun transferMoneyToBarker(userId: String?, mark: Double?)
    suspend fun closeTrade(tradeId: String?)
    suspend fun changeMembersList(tradeId: String?, member: Auctioneer?)

}