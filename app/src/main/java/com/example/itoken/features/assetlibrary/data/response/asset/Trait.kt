package com.example.itoken.features.assetlibrary.data.response.asset

import com.google.gson.annotations.SerializedName

data class Trait(
    @SerializedName("display_type")
    val displayType: Any,
    @SerializedName("max_value")
    val maxValue: Any,
    val order: Any,
    @SerializedName("trait_count")
    val traitCount: Int,
    @SerializedName("trait_type")
    val traitType: String,
    val value: String
)
