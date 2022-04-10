package com.example.itoken.features.user.domain.model

import com.example.itoken.common.entity.BaseAsset
import com.example.itoken.features.user.presentation.model.ItemAssetBrief

data class ItemAsset(
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
    fun toItemAssetBrief() = ItemAssetBrief(
        tokenId,
        imageUrl,
        creatorName,
        tokenName,
        price,
        likes
    )
}