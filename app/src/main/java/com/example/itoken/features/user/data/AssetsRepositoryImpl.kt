package com.example.itoken.features.user.data

import com.example.itoken.features.user.data.db.dao.AssetsDao
import com.example.itoken.features.user.domain.model.ItemAsset
import com.example.itoken.features.user.domain.repository.AssetsRepository
import javax.inject.Inject

class AssetsRepositoryImpl @Inject constructor(
    private val database: AssetsDao
) : AssetsRepository {

    override suspend fun getAllCreated(name: String): List<ItemAsset> {
        val result = arrayListOf<ItemAsset>()
        for (asset in database.getAllCreated(name)) {
            result.add(asset.toItemAsset())
        }
        return result
    }

    override suspend fun getAllCollected(name: String) : List<ItemAsset> {
        val result = arrayListOf<ItemAsset>()
        for (asset in database.getAllCollected(name)) {
            result.add(asset.toItemAsset())
        }
        return result
    }

    override suspend fun getAllFavourites(name: String) : List<ItemAsset> {
        val result = arrayListOf<ItemAsset>()
        for (asset in database.getAllFavourites(name)) {
            result.add(asset.toItemAsset())
        }
        return result
    }

    override suspend fun getAll() : List<ItemAsset> {
        val result = arrayListOf<ItemAsset>()
        for (asset in database.getAll()) {
            result.add(asset.toItemAsset())
        }
        return result
    }
}