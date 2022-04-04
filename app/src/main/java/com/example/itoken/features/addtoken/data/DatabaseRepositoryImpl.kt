package com.example.itoken.features.addtoken.data

import com.example.itoken.features.addtoken.data.db.dao.AssetsDao
import com.example.itoken.features.addtoken.data.entity.DatabaseAsset
import com.example.itoken.features.addtoken.domain.model.AssetModel
import com.example.itoken.features.addtoken.domain.repository.DatabaseRepository
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val database: AssetsDao
) : DatabaseRepository {

    override suspend fun getAllCreated(name: String): List<AssetModel> {
        val result = arrayListOf<AssetModel>()
        for (asset in database.getAllCreated(name)) {
            result.add(asset.toAssetDto())
        }
        return result
    }

    override suspend fun getAllCollected(name: String) : List<AssetModel> {
        val result = arrayListOf<AssetModel>()
        for (asset in database.getAllCollected(name)) {
            result.add(asset.toAssetDto())
        }
        return result
    }

    override suspend fun getAllFavourites(name: String) : List<AssetModel> {
        val result = arrayListOf<AssetModel>()
        for (asset in database.getAllFavourites(name)) {
            result.add(asset.toAssetDto())
        }
        return result
    }

    override suspend fun add(asset: DatabaseAsset) = database.add(asset)

}