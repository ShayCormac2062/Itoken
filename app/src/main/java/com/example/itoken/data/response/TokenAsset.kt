package com.example.itoken.data.response

data class TokenAsset(
    val assets: List<Asset>,
    val next: String,
    val previous: Any
)
