package com.example.itoken.features.assetlibrary.data.response.collection

import com.example.itoken.features.assetlibrary.domain.model.InfoCollection

data class Collection(
    val banner_image_url: String?,
    val chat_url: String?,
    val created_date: String?,
    val default_to_fiat: Boolean?,
    val description: String?,
    val dev_buyer_fee_basis_points: String?,
    val dev_seller_fee_basis_points: String?,
    val discord_url: String?,
    val display_data: DisplayData?,
    val external_url: String?,
    val featured: Boolean?,
    val featured_image_url: String?,
    val hidden: Boolean?,
    val image_url: String?,
    val instagram_username: String?,
    val is_nsfw: Boolean?,
    val is_subject_to_whitelist: Boolean?,
    val large_image_url: String?,
    val medium_username: String?,
    val name: String?,
    val only_proxied_transfers: Boolean?,
    val opensea_buyer_fee_basis_points: String?,
    val opensea_seller_fee_basis_points: String?,
    val payout_address: String?,
    val primary_asset_contracts: List<Any>?,
    val require_email: Boolean?,
    val safelist_request_status: String?,
    val short_description: Any?,
    val slug: String?,
    val stats: Stats?,
    val telegram_url: String?,
    val traits: Traits?,
    val twitter_username: String?,
    val wiki_url: String?
) {

    fun toInfoCollection() = InfoCollection(
        banner_image_url ?: image_url,
        created_date,
        description,
        image_url,
        name,
        dev_seller_fee_basis_points?.toInt(),
        stats?.num_owners,
        medium_username ?: instagram_username ?: twitter_username ?: slug
    )

}