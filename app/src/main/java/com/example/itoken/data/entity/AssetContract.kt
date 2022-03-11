package com.example.itoken.data.entity

data class AssetContract(
    val address: String,
    val asset_contract_type: String,
    val buyer_fee_basis_points: Int,
    val created_date: String,
    val default_to_fiat: Boolean,
    val description: String,
    val dev_buyer_fee_basis_points: Int,
    val dev_seller_fee_basis_points: Int,
    val external_link: String,
    val image_url: String,
    val name: String,
    val nft_version: String,
    val only_proxied_transfers: Boolean,
    val opensea_buyer_fee_basis_points: Int,
    val opensea_seller_fee_basis_points: Int,
    val opensea_version: Any,
    val owner: Int,
    val payout_address: String,
    val schema_name: String,
    val seller_fee_basis_points: Int,
    val symbol: String,
    val total_supply: String
)