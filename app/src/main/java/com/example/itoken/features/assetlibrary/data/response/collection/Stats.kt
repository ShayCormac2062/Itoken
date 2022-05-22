package com.example.itoken.features.assetlibrary.data.response.collection

import com.google.gson.annotations.SerializedName

data class Stats(
    @SerializedName("average_price")
    val averagePrice: Double,
    val count: Double,
    @SerializedName("floor_price")
    val floorPrice: Int,
    @SerializedName("market_cap")
    val marketCap: Double,
    @SerializedName("num_owners")
    val numOwners: Int,
    @SerializedName("num_reports")
    val numReports: Int,
    @SerializedName("one_day_average_price")
    val oneDayAveragePrice: Double,
    @SerializedName("one_day_change")
    val oneDayChange: Double,
    @SerializedName("one_day_sales")
    val oneDaySales: Double,
    @SerializedName("one_day_volume")
    val oneDayVolume: Double,
    @SerializedName("seven_day_average_price")
    val sevenDayAveragePrice: Double,
    @SerializedName("seven_day_change")
    val sevenDayChange: Double,
    @SerializedName("seven_day_sales")
    val sevenDaySales: Double,
    @SerializedName("seven_day_volume")
    val sevenDayVolume: Double,
    @SerializedName("thirty_day_average_price")
    val thirtyDayAveragePrice: Double,
    @SerializedName("thirty_day_change")
    val thirtyDayChange: Double,
    @SerializedName("thirty_day_sales")
    val thirtyDaySales: Double,
    @SerializedName("thirty_day_volume")
    val thirtyDayVolume: Double,
    @SerializedName("total_sales")
    val totalSales: Double,
    @SerializedName("total_supply")
    val totalSupply: Double,
    @SerializedName("total_volume")
    val totalVolume: Double
)