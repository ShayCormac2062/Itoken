package com.example.itoken.features.user.data

import com.example.itoken.common.db.dao.AddAssetDao
import com.example.itoken.features.user.data.db.dao.AssetsDao
import com.example.itoken.features.user.domain.model.ItemAsset
import com.example.itoken.features.user.domain.repository.AssetsRepository
import com.example.itoken.utils.DispatcherProvider
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AssetsRepositoryImpl @Inject constructor(
    private val assetsDatabase: AssetsDao,
    private val addAssetsDatabase: AddAssetDao,
    private val scope: DispatcherProvider
) : AssetsRepository {

    override suspend fun getAllCreated(name: String): List<ItemAsset> {
        val result = arrayListOf<ItemAsset>()
        withContext(scope.IO) {
            for (asset in assetsDatabase.getAllCreated(name)) {
                result.add(asset.toItemAsset())
            }
        }
        return result
    }

    override suspend fun getAllCollected(name: String) : List<ItemAsset> {
        val result = arrayListOf<ItemAsset>()
        withContext(scope.IO) {
            for (asset in assetsDatabase.getAllCollected(name)) {
                result.add(asset.toItemAsset())
            }
        }
        return result
    }

    override suspend fun getAllFavourites(name: String) : List<ItemAsset> {
        val result = arrayListOf<ItemAsset>()
        withContext(scope.IO) {
            for (asset in assetsDatabase.getAllFavourites(name)) {
                result.add(asset.toItemAsset())
            }
        }
        return result
    }

    override suspend fun getAll() : List<ItemAsset> {
        val result = arrayListOf<ItemAsset>()
        withContext(scope.IO) {
            for (asset in assetsDatabase.getAll()) {
                result.add(asset.toItemAsset())
            }
        }
        return result
    }

    override suspend fun addAll(assets: List<ItemAsset>) {
        assets.forEach {
            addAssetsDatabase.add(it.toDatabaseAsset())
        }
    }
}