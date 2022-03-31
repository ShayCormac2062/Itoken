package com.example.itoken.features.assetlibrary.presentation.model

data class AssetBrief(
    var tokenId: String?,
    var imageUrl: String?,
    var creatorName: String?,
    var tokenName: String?,
    var price: Int?,
    var likes: Int?,
)
