package com.example.itoken.features.assetlibrary.presentation.model

import com.example.itoken.common.entity.BaseAssetBrief

data class AssetBrief(
    override val tokenId: String?,
    override val imageUrl: String?,
    override val creatorName: String?,
    override val tokenName: String?,
    override val price: Long?,
    override val likes: Long?,
) : BaseAssetBrief(
    tokenId,
    imageUrl,
    creatorName,
    tokenName,
    price,
    likes
)
