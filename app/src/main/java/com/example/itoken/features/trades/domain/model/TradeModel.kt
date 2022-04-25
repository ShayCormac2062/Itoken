package com.example.itoken.features.trades.domain.model

import java.io.Serializable

data class TradeModel(
    val token: Lot,
    val author: String?,
    val price: Long?,
    var isActive: Boolean,
    var candidates: ArrayList<Auctioneer>?,
) : Serializable
