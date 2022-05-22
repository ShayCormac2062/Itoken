package com.example.itoken.features.assetlibrary.domain.repository

import com.example.itoken.features.assetlibrary.domain.model.InfoCollection

interface CollectionRepository {

    suspend fun getCollections(): List<InfoCollection>

}
