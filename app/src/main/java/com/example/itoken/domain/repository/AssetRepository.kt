package com.example.itoken.domain.repository

import com.example.itoken.domain.model.InfoAsset

interface AssetRepository {

    suspend fun getAssetsBrief(): List<InfoAsset>
    suspend fun getAssets(): List<InfoAsset>

}
