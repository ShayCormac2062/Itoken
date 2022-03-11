package com.example.itoken.data.entity

data class PaymentToken(
    val address: String,
    val decimals: Int,
    val eth_price: String,
    val id: Int,
    val image_url: String,
    val name: String,
    val symbol: String,
    val usd_price: String
)