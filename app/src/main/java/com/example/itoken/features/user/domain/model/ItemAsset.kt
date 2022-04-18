package com.example.itoken.features.user.domain.model

import com.example.itoken.common.db.model.DatabaseAsset
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
        (creatorName?.let {
            when {
                it.length > 18 -> StringBuilder().append(it.substring(0, 15)).append("...")
                it == "" -> "Имя автора не указано"
                else -> it
            }
        } ?: "Автор неизвестен").toString(),
        (tokenName?.let {
            (if (it.length > 32) {
                StringBuilder().append(it.substring(0, 29))
                    .append("...")
            } else it)
        } ?: "Название не указано").toString(),
        price,
        likes
    )

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