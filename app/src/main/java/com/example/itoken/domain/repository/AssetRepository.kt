package com.example.itoken.domain.repository

import com.example.itoken.data.response.Asset

interface AssetRepository {

    suspend fun getAssetsBrief(): List<Asset>
    suspend fun getAssets(): List<Asset>

}