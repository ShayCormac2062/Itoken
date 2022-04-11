package com.example.itoken.features.user.domain.repository

import com.example.itoken.features.user.domain.model.ItemAsset


interface AssetsRepository {

    suspend fun getAllCreated(name: String): List<ItemAsset>

    suspend fun getAllCollected(name: String): List<ItemAsset>

    suspend fun getAllFavourites(name: String): List<ItemAsset>

    suspend fun getAll(): List<ItemAsset>
}