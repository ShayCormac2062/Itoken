package com.example.itoken.features.assetlibrary.data

import com.example.itoken.features.assetlibrary.data.response.Asset
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
                if (assets.indexOf(asset) % rand == 0 && asset.name != null) result.add(setup(asset).toInfoAsset())
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
            result.add(setup(asset).toInfoAsset())
        }
        return result
    }

    private fun setup(asset: Asset): Asset {
        return asset.apply {
            creator?.user?.username = (creator?.user?.username?.let {
                (if (it.length > 18) {
                    StringBuilder().append(it.substring(0, 15))
                        .append("...")
                } else it)
            } ?: "Автор неизвестен").toString()
            name = (name?.let {
                (if (it.length > 32) {
                    StringBuilder().append(it.substring(0, 29))
                        .append("...")
                } else it)
            } ?: "Название не указано").toString()
            try {
                asset_contract?.address = StringBuilder()
                    .append(asset_contract?.address?.substring(14, 28))
                    .append("...").toString()
            } catch (e: Exception) {}
            token_id = token_id?.let {
                if (it.length > 10) it.substring(0, 9)
                else it
            }.toString()
            owner?.user?.username = owner?.user?.username?.let {
                if (it.length > 30) "Имя не указано"
                else it
            } ?: "Имя не указано"
        }
    }

}
