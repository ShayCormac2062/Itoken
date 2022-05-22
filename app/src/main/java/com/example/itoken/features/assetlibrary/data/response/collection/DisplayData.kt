package com.example.itoken.features.assetlibrary.data.response.collection

import com.google.gson.annotations.SerializedName

data class DisplayData(
    @SerializedName("card_display_style")
    val cardDisplayStyle: String?
)