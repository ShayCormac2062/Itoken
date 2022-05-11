package com.example.itoken.features.assetlibrary.data.response.collection

data class Stats(
    val average_price: Double,
    val count: Double,
    val floor_price: Int,
    val market_cap: Double,
    val num_owners: Int,
    val num_reports: Int,
    val one_day_average_price: Double,
    val one_day_change: Double,
    val one_day_sales: Double,
    val one_day_volume: Double,
    val seven_day_average_price: Double,
    val seven_day_change: Double,
    val seven_day_sales: Double,
    val seven_day_volume: Double,
    val thirty_day_average_price: Double,
    val thirty_day_change: Double,
    val thirty_day_sales: Double,
    val thirty_day_volume: Double,
    val total_sales: Double,
    val total_supply: Double,
    val total_volume: Double
)