package com.example.itoken.features.assetlibrary.data.response.asset

import com.google.gson.annotations.SerializedName

data class Owner(
    val address: String,
    val config: String,
    @SerializedName("profile_img_url")
    val profileImgUrl: String,
    val user: User
)
