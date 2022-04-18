package com.example.itoken.features.user.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.itoken.common.db.model.DatabaseAsset

@Dao
interface AssetsDao {

    @Query("SELECT * FROM database_assets WHERE creator_name = :name")
    suspend fun getAllCreated(name: String): List<DatabaseAsset>

    @Query("SELECT * FROM database_assets WHERE owner_name = :name")
    suspend fun getAllCollected(name: String): List<DatabaseAsset>

    @Query("SELECT * FROM database_assets WHERE owner_name != :name and creator_name != :name")
    suspend fun getAllFavourites(name: String): List<DatabaseAsset>

    @Query("SELECT * FROM database_assets")
    suspend fun getAll(): List<DatabaseAsset>

    @Query("DELETE FROM database_assets")
    suspend fun deleteAll()

}