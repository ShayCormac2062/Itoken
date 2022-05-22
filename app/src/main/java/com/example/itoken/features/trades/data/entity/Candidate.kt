package com.example.itoken.features.trades.data.entity

import com.example.itoken.features.trades.domain.model.Auctioneer

data class Candidate(
    val stringId: String?,
    val name: String?,
    var price: Long?,
    var imageUrl: String?,
)
