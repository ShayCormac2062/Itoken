package com.example.itoken.features.assetlibrary.data.response.asset

import com.google.gson.annotations.SerializedName

data class Creator(
    val address: String,
    val config: String,
    @SerializedName("profile_img_url")
    val profileImageUrl: String,
    val user: User
)
