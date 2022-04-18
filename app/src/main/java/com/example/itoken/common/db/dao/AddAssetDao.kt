package com.example.itoken.common.db.dao

import androidx.room.*
import com.example.itoken.common.db.model.DatabaseAsset

@Dao
interface AddAssetDao {
    @Insert
    suspend fun add(asset: DatabaseAsset)
}