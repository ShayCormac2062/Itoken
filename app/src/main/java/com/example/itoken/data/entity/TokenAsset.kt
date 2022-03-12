package com.example.itoken.data.entity

data class TokenAsset(
    val assets: List<Asset>,
    val next: String,
    val previous: Any
)