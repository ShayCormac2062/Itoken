package com.example.itoken.data.entity

data class LastSale(
    val asset: AssetDecimals,
    val asset_bundle: Any,
    val auction_type: Any,
    val created_date: String,
    val event_timestamp: String,
    val event_type: String,
    val payment_token: PaymentToken,
    val quantity: String,
    val total_price: String,
    val transaction: Transaction
)