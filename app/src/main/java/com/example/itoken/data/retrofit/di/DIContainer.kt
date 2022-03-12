package com.example.itoken.data.retrofit.di

import android.util.JsonReader
import com.example.itoken.data.retrofit.APIService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DIContainer {

    private const val PARSE_ROOT = "https://raw.githubusercontent.com/ShayCormac2062/MyNFTApi/"

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