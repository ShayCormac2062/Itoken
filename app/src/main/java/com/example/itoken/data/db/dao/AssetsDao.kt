package com.example.itoken.data.db.dao

import androidx.room.*
import com.example.itoken.domain.enitty.ShowableAsset

@Dao
interface AssetsDao {

    @Query("SELECT * FROM showable_assets WHERE creator_name = :name")
    suspend fun getAllCreated(name: String): List<ShowableAsset>

    @Query("SELECT * FROM showable_assets WHERE owner_name = :name")
    suspend fun getAllCollected(name: String): List<ShowableAsset>

    @Query("SELECT * FROM showable_assets WHERE owner_name != :name")
    suspend fun getAllFavourites(name: String): List<ShowableAsset>

    @Insert
    suspend fun add(asset: ShowableAsset)

}