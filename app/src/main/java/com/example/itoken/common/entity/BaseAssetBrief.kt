package com.example.itoken.common.entity

open class BaseAssetBrief(
    open val tokenId: String?,
    open val imageUrl: String?,
    open val creatorName: String?,
    open val tokenName: String?,
    open val price: Int?,
    open val likes: Int?,
)