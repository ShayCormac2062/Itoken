package com.example.itoken.features.assetlibrary.data.retrofit

import com.example.itoken.features.assetlibrary.data.response.asset.TokenAsset
import com.example.itoken.features.assetlibrary.data.response.collection.CollectionResponse
import retrofit2.http.GET

interface APIService {

    @GET("master/assets.json")
    suspend fun getAssets(): TokenAsset

    @GET("master/collections.json")
    suspend fun getCollections(): CollectionResponse

}
