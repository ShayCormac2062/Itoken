package com.example.itoken.features.user.data

import com.example.itoken.common.db.dao.AddAssetDao
import com.example.itoken.features.user.data.db.dao.AssetsDao
import com.example.itoken.features.user.domain.model.ItemAsset
import com.example.itoken.features.user.domain.repository.AssetsRepository
import com.example.itoken.utils.DispatcherProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AssetsRepositoryImpl @Inject constructor(
    private val assetsDatabase: AssetsDao,
    private val addAssetsDatabase: AddAssetDao,
    private val scope: DispatcherProvider,
    private val ref: DatabaseReference,
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

    override suspend fun getAllCollected(name: String): List<ItemAsset> {
        val result = arrayListOf<ItemAsset>()
        withContext(scope.IO) {
            for (asset in assetsDatabase.getAllCollected(name)) {
                result.add(asset.toItemAsset())
            }
        }
        return result
    }

    override suspend fun getAllTraded(name: String, userId: String?): List<ItemAsset> {
        val result = arrayListOf<ItemAsset>()
        withContext(scope.IO) {
            val usersFromFirebase = retrieveTrades(userId)
            for (asset in assetsDatabase.getAllTraded(name)) {
                result.add(asset.toItemAsset())
            }
            for (asset in usersFromFirebase) {
                if (!result.contains(asset)) result.add(asset)
            }
        }
        return result
    }

    private suspend fun retrieveTrades(userId: String?): List<ItemAsset> {
        val result = arrayListOf<ItemAsset>()
        val snapshot = ref.get().await()
        try {
            userId?.let {
                val snapShot = snapshot.child("users").child(userId).child("bought_assets")
                for (asset in snapShot.children) {
                    result.add(retrieveAsset(asset))
                }
            }
        } catch (e: Exception) { }
        return result
    }

    private fun retrieveAsset(dto: DataSnapshot?) = ItemAsset(
        dto?.child("tokenId")?.value as String?,
        dto?.child("imagePreviewUrl")?.value as String?,
        dto?.child("imageUrl")?.value as String?,
        dto?.child("creatorName")?.value as String?,
        dto?.child("ownerName")?.value as String?,
        dto?.child("tokenName")?.value as String?,
        dto?.child("price")?.value as Long?,
        dto?.child("likes")?.value as Long?,
        dto?.child("description")?.value as String?,
        dto?.child("address")?.value as String?,
    )

    override suspend fun getAll(): List<ItemAsset> {
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