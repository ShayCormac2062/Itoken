package com.example.itoken.features.assetlibrary.data.repository

import com.example.itoken.features.assetlibrary.data.retrofit.APIService
import com.example.itoken.features.assetlibrary.domain.model.InfoCollection
import com.example.itoken.features.assetlibrary.domain.repository.CollectionRepository
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val api: APIService
) : CollectionRepository {

    override suspend fun getCollections(): List<InfoCollection> {
        val result = arrayListOf<InfoCollection>()
        val response = api.getCollections()
        for (collection in response.collections) {
            result.add(collection.toInfoCollection())
        }
        return result
    }

}