package com.example.itoken.features.addtoken.domain.repository

import com.example.itoken.features.addtoken.data.entity.DatabaseAsset
import com.example.itoken.features.addtoken.domain.model.AssetModel

interface DatabaseRepository {

    suspend fun getAllCreated(name: String): List<AssetModel>

    suspend fun getAllCollected(name: String): List<AssetModel>

    suspend fun getAllFavourites(name: String): List<AssetModel>

    suspend fun add(asset: DatabaseAsset)
}