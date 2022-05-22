package com.example.itoken.features.assetlibrary.data.response.asset

import com.example.itoken.features.assetlibrary.domain.model.InfoAsset
import com.google.gson.annotations.SerializedName

data class Asset(
    @SerializedName("animation_original_url")
    val animationOriginalUrl: String?,
    @SerializedName("animation_url")
    val animation_url: String?,
    @SerializedName("asset_contract")
    val assetContract: AssetContract?,
    @SerializedName("background_color")
    val backgroundColor: Any?,
    val collection: Collection?,
    val creator: Creator?,
    val decimals: Int?,
    val description: String?,
    @SerializedName("external_link")
    val externalLink: String?,
    val id: Int?,
    @SerializedName("image_original_url")
    val imageOriginalUrl: String?,
    @SerializedName("image_preview_url")
    val imagePreviewUrl: String?,
    @SerializedName("image_thumbnail_url")
    val imageThumbnailUrl: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("is_nsfw")
    val isNsfw: Boolean?,
    @SerializedName("is_presale")
    val isPresale: Boolean?,
    @SerializedName("last_sale")
    val lastSale: Any?,
    @SerializedName("listing_date")
    val listingDate: Any?,
    var name: String?,
    @SerializedName("num_sales")
    val numSales: Int?,
    val owner: Owner?,
    val permalink: String?,
    @SerializedName("sell_orders")
    val sellOrders: Any?,
    @SerializedName("token_id")
    var tokenId: String?,
    @SerializedName("token_metadata")
    val tokenMetadata: Any?,
    @SerializedName("top_bid")
    val topBid: Any?,
    val traits: List<Trait>?,
    @SerializedName("transfer_fee")
    val transferFee: Any?,
    @SerializedName("transfer_fee_payment_token")
    val transferFeePaymentToken: Any?
) {
    fun toInfoAsset() = InfoAsset(
            tokenId,
            imagePreviewUrl,
            imageUrl,
            creator?.user?.username,
            owner?.user?.username,
            name,
            assetContract?.sellerFeeBasisPoints.toString().toLong(),
            (0..599).random().toLong(),
            description ?: collection?.description,
            assetContract?.address
        )
}
