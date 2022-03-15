package com.example.itoken.data.retrofit.di

import com.example.itoken.data.retrofit.APIService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DIContainer {

    private const val PARSE_ROOT = "https://raw.githubusercontent.com/ShayCormac2062/MyNFTApi/"

    private val okhttp by lazy {
        OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .cache(null)
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