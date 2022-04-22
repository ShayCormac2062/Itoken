package com.example.itoken.common.entity

import com.example.itoken.features.addtoken.domain.model.AssetModel
import com.example.itoken.features.trades.domain.model.Lot

open class BaseAsset(
    open var tokenId: String?,
    open var imagePreviewUrl: String?,
    open var imageUrl: String?,
    open var creatorName: String?,
    open var ownerName: String?,
    open var tokenName: String?,
    open var price: Long?,
    open var likes: Long?,
    open var description: String?,
    open var address: String?,
) {
    fun toAssetModel() = AssetModel(
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