package com.example.itoken.features.assetlibrary.data.retrofit

import com.example.itoken.features.assetlibrary.data.response.TokenAsset
import retrofit2.http.GET

interface APIService {

    @GET("master/assets.json")
    suspend fun getAssets(): TokenAsset

}
