package com.example.itoken.features.addtoken.data

import com.example.itoken.common.db.dao.AddAssetDao
import com.example.itoken.common.db.model.DatabaseAsset
import com.example.itoken.features.addtoken.domain.repository.AddTokenRepository
import com.example.itoken.utils.DispatcherProvider
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddTokenRepositoryImpl @Inject constructor(
    private val database: AddAssetDao,
    private val firebase: DatabaseReference,
    private val scope: DispatcherProvider
) : AddTokenRepository {

    override suspend fun add(asset: DatabaseAsset) {
        withContext(scope.IO) {
            database.add(asset)
            firebase.child("assets").setValue(asset)
        }
    }

}