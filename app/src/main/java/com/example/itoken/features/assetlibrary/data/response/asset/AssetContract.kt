package com.example.itoken.features.assetlibrary.data.response.asset

import com.google.gson.annotations.SerializedName

data class AssetContract(
    var address: String,
    @SerializedName("asset_contract_type")
    val assetContractType: String,
    @SerializedName("buyer_fee_basis_points")
    val buyerFeeBasisPoints: Int,
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("default_to_fiat")
    val defaultToFiat: Boolean,
    val description: String,
    @SerializedName("dev_buyer_fee_basis_points")
    val devBuyerFeeBasisPoints: Int,
    @SerializedName("dev_seller_fee_basis_points")
    val devSellerFeeBasisPoints: Int,
    @SerializedName("external_link")
    val externalLink: Any,
    @SerializedName("image_url")
    val imageUrl: Any,
    val name: String,
    @SerializedName("nft_version")
    val nftVersion: Any,
    @SerializedName("only_proxied_transfers")
    val onlyProxyTransfers: Boolean,
    @SerializedName("opensea_buyer_fee_basis_points")
    val openSeaBuyerFeeBasisPoints: Int,
    @SerializedName("opensea_seller_fee_basis_points")
    val openSeaSellerFeeBasisPoints: Int,
    @SerializedName("opensea_version")
    val openSeaVersion: String,
    val owner: Int,
    @SerializedName("payout_address")
    val payoutAddress: Any,
    @SerializedName("schema_name")
    val schemaName: String,
    @SerializedName("seller_fee_basis_points")
    val sellerFeeBasisPoints: Int,
    val symbol: String,
    @SerializedName("total_supply")
    val totalSupply: Any
)
