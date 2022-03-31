package com.example.itoken.features.assetlibrary.data

import com.example.itoken.features.assetlibrary.data.retrofit.APIService
import com.example.itoken.features.assetlibrary.domain.model.InfoAsset
import com.example.itoken.features.assetlibrary.domain.repository.AssetRepository
import javax.inject.Inject

class AssetRepositoryImpl @Inject constructor(private val api: APIService) : AssetRepository {

    override suspend fun getAssetsBrief(): List<InfoAsset> {
        val result = arrayListOf<InfoAsset>()
        val rand = (1..35).random()
        try {
            val assets = api.getAssets().assets
            for (asset in assets) {
                if (assets.indexOf(asset) % rand == 0 && asset.name != null) result.add(asset.toInfoAsset())
                if (result.size == 10) return result
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    override suspend fun getAssets(): List<InfoAsset> {
        val result = arrayListOf<InfoAsset>()
        for (asset in api.getAssets().assets) {
            result.add(asset.toInfoAsset())
        }
        return result
    }

}
