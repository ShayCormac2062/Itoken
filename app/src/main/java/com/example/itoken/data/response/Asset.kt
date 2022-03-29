package com.example.itoken.data.response

import com.example.itoken.domain.model.InfoAsset

data class Asset(
    val animation_original_url: String?,
    val animation_url: String?,
    val asset_contract: AssetContract?,
    val background_color: Any?,
    val collection: Collection?,
    val creator: Creator?,
    val decimals: Int?,
    val description: String?,
    val external_link: String?,
    val id: Int?,
    val image_original_url: String?,
    val image_preview_url: String?,
    val image_thumbnail_url: String?,
    val image_url: String?,
    val is_nsfw: Boolean?,
    val is_presale: Boolean?,
    val last_sale: Any?,
    val listing_date: Any?,
    val name: String?,
    val num_sales: Int?,
    val owner: Owner?,
    val permalink: String?,
    val sell_orders: Any?,
    val token_id: String?,
    val token_metadata: Any?,
    val top_bid: Any?,
    val traits: List<Trait>?,
    val transfer_fee: Any?,
    val transfer_fee_payment_token: Any?
) {
    fun toInfoAsset() = InfoAsset(
            token_id ?: "0",
            image_preview_url,
            image_url,
            creator?.user?.username,
            owner?.user?.username,
            name,
            asset_contract?.seller_fee_basis_points ?: 0,
            (0..599).random(),
            description ?: collection?.description,
            asset_contract?.address
        )
}
