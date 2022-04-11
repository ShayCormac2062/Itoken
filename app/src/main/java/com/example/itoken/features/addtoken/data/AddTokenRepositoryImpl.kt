package com.example.itoken.features.addtoken.data

import com.example.itoken.features.addtoken.data.db.AddAssetDao
import com.example.itoken.common.db.model.DatabaseAsset
import com.example.itoken.features.addtoken.domain.repository.AddTokenRepository
import com.example.itoken.utils.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddTokenRepositoryImpl @Inject constructor(
    private val database: AddAssetDao,
    private val scope: DispatcherProvider
) : AddTokenRepository {

    override suspend fun add(asset: DatabaseAsset) {
        withContext(scope.IO) {
            database.add(asset)
        }
    }

}