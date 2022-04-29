package com.example.itoken.features.assetlibrary.domain.repository

import com.example.itoken.features.assetlibrary.domain.model.InfoAsset

interface AssetRepository {

    suspend fun getAssetsBrief(): List<InfoAsset>
    suspend fun getAssetsCheap(): List<InfoAsset>
    suspend fun getAssets(): List<InfoAsset>
    suspend fun getAssetsByCategory(category: String): List<InfoAsset>
    suspend fun getAssetsBySearch(request: String): List<InfoAsset>

}
