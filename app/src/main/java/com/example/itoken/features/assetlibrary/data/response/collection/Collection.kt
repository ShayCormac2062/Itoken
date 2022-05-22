package com.example.itoken.features.assetlibrary.data.response.collection

import com.example.itoken.features.assetlibrary.domain.model.InfoCollection
import com.google.gson.annotations.SerializedName

data class Collection(
    @SerializedName("banner_image_url")
    val bannerImageUrl: String?,
    @SerializedName("chat_url")
    val chatUrl: String?,
    @SerializedName("created_date")
    val createdDate: String?,
    @SerializedName("default_to_fiat")
    val defaultToFiat: Boolean?,
    val description: String?,
    @SerializedName("dev_buyer_fee_basis_points")
    val devBuyerFeeBasisPoints: String?,
    @SerializedName("dev_seller_fee_basis_points")
    val devSellerFeeBasisPoints: String?,
    @SerializedName("discord_url")
    val discordUrl: String?,
    @SerializedName("display_data")
    val displayData: DisplayData?,
    @SerializedName("external_url")
    val externalUrl: String?,
    val featured: Boolean?,
    @SerializedName("featured_image_url")
    val featuredImageUrl: String?,
    val hidden: Boolean?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("instagram_username")
    val instagramUsername: String?,
    @SerializedName("is_nsfw")
    val isNsfw: Boolean?,
    @SerializedName("is_subject_to_whitelist")
    val isSubjectToWhiteList: Boolean?,
    @SerializedName("large_image_url")
    val largeImageUrl: String?,
    @SerializedName("medium_username")
    val mediumUsername: String?,
    val name: String?,
    @SerializedName("only_proxied_transfers")
    val onlyProxyTransfers: Boolean?,
    @SerializedName("opensea_buyer_fee_basis_points")
    val openSeaBuyerFeeBasisPoints: String?,
    @SerializedName("opensea_seller_fee_basis_points")
    val openSeaSellerFeeBasisPoints: String?,
    @SerializedName("payout_address")
    val payoutAddress: String?,
    @SerializedName("primary_asset_contracts")
    val primaryAssetContracts: List<Any>?,
    @SerializedName("require_email")
    val requireEmail: Boolean?,
    @SerializedName("safelist_request_status")
    val safeListRequestStatus: String?,
    @SerializedName("short_description")
    val shortDescription: Any?,
    val slug: String?,
    val stats: Stats?,
    @SerializedName("telegram_url")
    val telegramUrl: String?,
    val traits: Traits?,
    @SerializedName("twitter_username")
    val twitterUsername: String?,
    @SerializedName("wiki_url")
    val wikiUrl: String?
) {

    fun toInfoCollection() = InfoCollection(
        bannerImageUrl ?: imageUrl,
        createdDate,
        description,
        imageUrl,
        name,
        devSellerFeeBasisPoints?.toInt(),
        stats?.numOwners,
        mediumUsername ?: instagramUsername ?: twitterUsername ?: slug
    )

}