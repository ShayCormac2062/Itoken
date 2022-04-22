package com.example.itoken.features.user.presentation.model

import com.example.itoken.common.entity.BaseAssetBrief

data class ItemAssetBrief(
    override var tokenId: String?,
    override var imageUrl: String?,
    override var creatorName: String?,
    override var tokenName: String?,
    override var price: Long?,
    override var likes: Long?,
) : BaseAssetBrief(
    tokenId,
    imageUrl,
    creatorName,
    tokenName,
    price,
    likes
)
