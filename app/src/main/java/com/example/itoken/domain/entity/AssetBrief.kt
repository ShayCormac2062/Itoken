package com.example.itoken.domain.entity

data class AssetBrief(
    var tokenId: Int,
    var imageUrl: String?,
    var creatorName: String?,
    var tokenName: String?,
    var price: Int?,
    var likes: Int?,
)
