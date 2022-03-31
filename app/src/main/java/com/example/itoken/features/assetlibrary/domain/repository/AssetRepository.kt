package com.example.itoken.features.assetlibrary.domain.repository

import com.example.itoken.features.assetlibrary.domain.model.InfoAsset

interface AssetRepository {

    suspend fun getAssetsBrief(): List<InfoAsset>
    suspend fun getAssets(): List<InfoAsset>

}
