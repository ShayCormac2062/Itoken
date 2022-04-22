package com.example.itoken.features.user.domain.model

import com.example.itoken.common.db.model.DatabaseAsset
import com.example.itoken.common.entity.BaseAsset
import com.example.itoken.features.user.presentation.model.ItemAssetBrief
import java.io.Serializable

data class ItemAsset(
    override var tokenId: String?,
    override var imagePreviewUrl: String?,
    override var imageUrl: String?,
    override var creatorName: String?,
    override var ownerName: String?,
    override var tokenName: String?,
    override var price: Long?,
    override var likes: Long?,
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
), Serializable {
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