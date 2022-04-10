package com.example.itoken.features.assetlibrary.domain.model

import com.example.itoken.common.entity.BaseAsset
import com.example.itoken.features.assetlibrary.presentation.model.AssetBrief

data class InfoAsset(
    override var tokenId: String?,
    override var imagePreviewUrl: String?,
    override var imageUrl: String?,
    override var creatorName: String?,
    override var ownerName: String?,
    override var tokenName: String?,
    override var price: Int?,
    override var likes: Int?,
    override var description: String?,
    override var address: String?,
) : BaseAsset(
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
