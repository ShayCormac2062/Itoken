package com.example.itoken.di.module

import android.content.Context
import com.example.itoken.App
import com.example.itoken.features.assetlibrary.data.AssetRepositoryImpl
import com.example.itoken.features.assetlibrary.data.retrofit.APIService
import com.example.itoken.features.assetlibrary.domain.repository.AssetRepository
import com.example.itoken.features.assetlibrary.domain.usecase.GetAssetsBriefUseCase
import com.example.itoken.features.assetlibrary.domain.usecase.GetAssetsUseCase
import com.example.itoken.utils.DispatcherProvider
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideContext(app: App): Context = app.applicationContext

    @Provides
    fun getAssetsBriefUseCase(
        repository: AssetRepository,
        scope: DispatcherProvider
    ): GetAssetsBriefUseCase = GetAssetsBriefUseCase(repository, scope)

    @Provides
    fun getAssetsUseCase(
        repository: AssetRepository,
        scope: DispatcherProvider
    ): GetAssetsUseCase = GetAssetsUseCase(repository, scope)

    @Provides
    fun assetRepository(
        api: APIService
    ): AssetRepository = AssetRepositoryImpl(api)
}
