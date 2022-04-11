package com.example.itoken.features.addtoken.data.db

import androidx.room.*
import com.example.itoken.common.db.model.DatabaseAsset

@Dao
interface AddAssetDao {
    @Insert
    suspend fun add(asset: DatabaseAsset)
}