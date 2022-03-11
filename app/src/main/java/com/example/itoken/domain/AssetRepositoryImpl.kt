package com.example.itoken.domain

import com.example.itoken.data.entity.Asset
import com.example.itoken.data.retrofit.di.DIContainer
import com.example.itoken.domain.repository.AssetRepository

class AssetRepositoryImpl : AssetRepository {

    override suspend fun getAssetsBrief(): List<Asset> {
        val result = arrayListOf<Asset>()
        val rand = (0..60).random()
        try {
            val assets = DIContainer.api.getAssets().assets
            for (asset in assets) {
                if (assets.indexOf(asset) % rand == 0) result.add(asset)
                if (result.size == 10) return result
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    override suspend fun getAssets(): List<Asset> {
        val result = arrayListOf<Asset>()
        for (asset in DIContainer.api.getAssets().assets) {
            result.add(asset)
        }
        return result
    }

}