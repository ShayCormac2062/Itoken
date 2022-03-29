package com.example.itoken.data.db.dao

import androidx.room.*
import com.example.itoken.data.db.entity.DatabaseAsset
import com.google.gson.reflect.TypeToken

@Dao
interface AssetsDao {

    @Query("SELECT * FROM database_assets WHERE creator_name = :name")
    suspend fun getAllCreated(name: String): List<DatabaseAsset>

    @Query("SELECT * FROM database_assets WHERE owner_name = :name")
    suspend fun getAllCollected(name: String): List<DatabaseAsset>

    @Query("SELECT * FROM database_assets WHERE owner_name != :name")
    suspend fun getAllFavourites(name: String): List<DatabaseAsset>

    @Insert
    suspend fun add(asset: DatabaseAsset)

}