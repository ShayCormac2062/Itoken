package com.example.itoken.features.trades.data.entity

import com.example.itoken.features.trades.domain.model.Auctioneer

data class Candidate(
    val name: String?,
    var price: Long?,
) {
    fun toAuctioneer() = Auctioneer(
        name,
        price
    )
}
