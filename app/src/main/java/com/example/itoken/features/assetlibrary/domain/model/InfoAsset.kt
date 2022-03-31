package com.example.itoken.features.assetlibrary.domain.model

import com.example.itoken.features.assetlibrary.presentation.model.AssetBrief

data class InfoAsset(
    var tokenId: String?,
    var imagePreviewUrl: String?,
    var imageUrl: String?,
    var creatorName: String?,
    var ownerName: String?,
    var tokenName: String?,
    var price: Int?,
    var likes: Int?,
    var description: String?,
    var address: String?,
) {
    fun toAssetBrief() = AssetBrief(
        tokenId,
        imageUrl,
        creatorName,
        tokenName,
        price,
        likes
    )
}
