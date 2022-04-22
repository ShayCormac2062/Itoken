package com.example.itoken.features.trades.data.entity

import com.example.itoken.features.trades.domain.model.Lot

data class TradableAsset(
    var tokenId: String?,
    var imagePreviewUrl: String?,
    var imageUrl: String?,
    var creatorName: String?,
    var ownerName: String?,
    var tokenName: String?,
    var price: Long?,
    var likes: Long?,
    var description: String?,
    var address: String?,
) {

    fun toLot() = Lot(
        tokenId,
        imagePreviewUrl,
        imageUrl,
        creatorName,
        ownerName,
        tokenName,
        price,
        likes,
        description,
        address
    )

}
