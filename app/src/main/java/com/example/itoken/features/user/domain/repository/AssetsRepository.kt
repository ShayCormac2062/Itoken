package com.example.itoken.features.user.domain.repository

import com.example.itoken.features.user.domain.model.ItemAsset

interface AssetsRepository {

    suspend fun getAllCreated(name: String): List<ItemAsset>

    suspend fun getAllCollected(name: String): List<ItemAsset>

    suspend fun getAllTraded(name: String): List<ItemAsset>

    suspend fun getAll(): List<ItemAsset>

    suspend fun addAll(assets: List<ItemAsset>)
}