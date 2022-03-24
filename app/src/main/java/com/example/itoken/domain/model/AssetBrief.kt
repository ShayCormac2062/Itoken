package com.example.itoken.domain.model

data class AssetBrief(
    var tokenId: Int,
    var imageUrl: String?,
    var creatorName: String?,
    var tokenName: String?,
    var price: Int?,
    var likes: Int?,
)