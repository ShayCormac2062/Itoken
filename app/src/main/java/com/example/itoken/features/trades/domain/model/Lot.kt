package com.example.itoken.features.trades.domain.model

import com.example.itoken.common.entity.BaseAsset
import com.example.itoken.features.trades.data.entity.TradableAsset
import java.io.Serializable

data class Lot(
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
) {

    fun toTradableAsset() = TradableAsset(
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
