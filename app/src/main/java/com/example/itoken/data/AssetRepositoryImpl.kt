package com.example.itoken.data

import com.example.itoken.data.response.Asset
import com.example.itoken.data.retrofit.APIService
import com.example.itoken.domain.repository.AssetRepository
import javax.inject.Inject

class AssetRepositoryImpl @Inject constructor(private val api: APIService) : AssetRepository {

    override suspend fun getAssetsBrief(): List<Asset> {
        val result = arrayListOf<Asset>()
        val rand = (1..35).random()
        try {
            val assets = api.getAssets().assets
            for (asset in assets) {
                if (assets.indexOf(asset) % rand == 0 && asset.name != null) result.add(asset)
                if (result.size == 10) return result
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    override suspend fun getAssets(): List<Asset> {
        val result = arrayListOf<Asset>()
        for (asset in api.getAssets().assets) {
            result.add(asset)
        }
        return result
    }

}