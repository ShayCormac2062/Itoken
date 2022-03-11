package com.example.itoken.data.retrofit

import com.example.itoken.data.entity.TokenAsset
import retrofit2.http.GET

interface APIService {

    @GET("v1/assets/?format=json")
    suspend fun getAssets(): TokenAsset

}