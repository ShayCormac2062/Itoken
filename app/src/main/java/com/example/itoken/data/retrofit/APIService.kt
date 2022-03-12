package com.example.itoken.data.retrofit

import com.example.itoken.data.entity.TokenAsset
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("master/assets.json")
    suspend fun getAssets(): TokenAsset

}