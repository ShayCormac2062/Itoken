package com.example.itoken.features.addtoken.data

import com.example.itoken.features.addtoken.data.db.AddAssetDao
import com.example.itoken.common.db.model.DatabaseAsset
import com.example.itoken.features.addtoken.domain.repository.AddTokenRepository
import javax.inject.Inject

class AddTokenRepositoryImpl @Inject constructor(
    private val database: AddAssetDao
) : AddTokenRepository {

    override suspend fun add(asset: DatabaseAsset) = database.add(asset)

}