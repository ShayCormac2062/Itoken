package com.example.itoken.domain.repository

import com.example.itoken.data.entity.Asset

interface AssetRepository {

    suspend fun getAssetsBrief(): List<Asset>
    suspend fun getAssets(): List<Asset>

}