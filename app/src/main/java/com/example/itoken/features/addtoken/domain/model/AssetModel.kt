package com.example.itoken.features.addtoken.domain.model

import com.example.itoken.common.db.model.DatabaseAsset

data class AssetModel(
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
    fun toDatabaseAsset() = DatabaseAsset(
        0,
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
