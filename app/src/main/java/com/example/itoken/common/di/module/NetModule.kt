package com.example.itoken.common.di.module

import com.example.itoken.features.assetlibrary.data.retrofit.APIService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetModule {

    private val root = "https://raw.githubusercontent.com/ShayCormac2062/MyNFTApi/"

    @Provides
    fun provideOkHttp(): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .cache(null)
            .build()

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun provideRetrofit(
        okHttp: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .client(okHttp)
            .baseUrl(root)
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Provides
    fun provideApi(
        retrofit: Retrofit
    ): APIService =
        retrofit.create(APIService::class.java)

}
