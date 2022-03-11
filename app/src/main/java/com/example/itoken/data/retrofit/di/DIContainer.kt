package com.example.itoken.data.retrofit.di

import com.example.itoken.data.retrofit.APIService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DIContainer {

    private const val PARSE_ROOT = "https://api.opensea.io/api/"

    private val okhttp by lazy {
        OkHttpClient.Builder()
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .client(okhttp)
            .baseUrl(PARSE_ROOT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: APIService by lazy {
        retrofit.create(APIService::class.java)
    }
}