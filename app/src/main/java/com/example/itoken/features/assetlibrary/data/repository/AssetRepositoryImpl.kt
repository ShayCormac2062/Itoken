package com.example.itoken.features.assetlibrary.data.repository

import com.example.itoken.features.assetlibrary.data.category.CategoryController
import com.example.itoken.features.assetlibrary.data.response.asset.Asset
import com.example.itoken.features.assetlibrary.data.retrofit.APIService
import com.example.itoken.features.assetlibrary.domain.model.InfoAsset
import com.example.itoken.features.assetlibrary.domain.repository.AssetRepository
import javax.inject.Inject

class AssetRepositoryImpl @Inject constructor(
    private val api: APIService,
) : AssetRepository {

    override suspend fun getAssetsBrief(): List<InfoAsset> {
        val result = arrayListOf<InfoAsset>()
        val rand = (1..35).random()
        try {
            val assets = api.getAssets().assets
            for (asset in assets) {
                if (assets.indexOf(asset) % rand == 0 && asset.name != null) result.add(setup(asset).toInfoAsset())
                if (result.size == 10) return result
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    override suspend fun getAssetsCheap(): List<InfoAsset> {
        val result = arrayListOf<InfoAsset>()
        val rand = (1..35).random()
        try {
            val assets = api.getAssets().assets
            for (asset in assets) {
                if (assets.indexOf(asset) % rand == 0 && asset.name != null) {
                    result.add(setup(asset).toInfoAsset().apply {
                        price = price?.minus((50..150).random())
                    })
                }
                if (result.size == 25) return result
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    override suspend fun getAssets(): List<InfoAsset> {
        val result = arrayListOf<InfoAsset>()
        for (asset in api.getAssets().assets) {
            result.add(setup(asset).toInfoAsset())
        }
        return result
    }

    override suspend fun getAssetsByCategory(category: String): List<InfoAsset> {
        val result = arrayListOf<InfoAsset>()
        for (asset in api.getAssets().assets) {
            for (word in CategoryController.getList(category)) {
                if (asset.name?.contains(word) == true) {
                    result.add(asset.toInfoAsset())
                    break
                }
            }
        }
        return result
    }

    override suspend fun getAssetsBySearch(request: String): List<InfoAsset> {
        val result = arrayListOf<InfoAsset>()
        for (asset in api.getAssets().assets) {
            val newAsset = setup(asset).toInfoAsset()
            if (newAsset.tokenName?.contains(request) == true) result.add(newAsset)
        }
        return result
    }

    private fun setup(asset: Asset): Asset {
        return asset.apply {
            try {
                assetContract?.address = StringBuilder()
                    .append(assetContract?.address?.substring(14, 28))
                    .append("...").toString()
            } catch (e: Exception) {}
            tokenId = tokenId?.let {
                if (it.length > 10) it.substring(0, 9)
                else it
            }.toString()
            owner?.user?.username = owner?.user?.username?.let {
                if (it.length > 23) "Имя не указано"
                else it
            } ?: "Имя не указано"
        }
    }

}
