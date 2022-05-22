package com.example.itoken.features.trades.data.entity

import com.example.itoken.features.trades.domain.model.Auctioneer
import com.example.itoken.features.trades.domain.model.TradeModel

data class Trade(
    val id: Long,
    val token: TradingAsset,
    val author: String?,
    val price: Long?,
    var isActive: Boolean,
    var candidates: ArrayList<Auctioneer>?,
) {
    fun toTradeModel() = TradeModel(
        token.toLot(),
        author,
        price,
        isActive,
        candidates
    )
}
