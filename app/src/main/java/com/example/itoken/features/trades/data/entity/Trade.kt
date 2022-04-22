package com.example.itoken.features.trades.data.entity

import com.example.itoken.features.trades.domain.model.Auctioneer
import com.example.itoken.features.trades.domain.model.TradeModel
import com.google.gson.Gson

data class Trade(
    val id: Long,
    val token: TradableAsset,
    val author: String?,
    val price: Long?,
    var isActive: Boolean,
    var candidates: String?,
) {
    fun toTradeModel() = TradeModel(
        token.toLot(),
        author,
        price,
        isActive,
        changeToList(candidates)
    )

    private fun changeToList(candidates: String?): List<Auctioneer> =
        Gson().fromJson(candidates, Array<Auctioneer>::class.java)?.toList() ?: arrayListOf()

}
