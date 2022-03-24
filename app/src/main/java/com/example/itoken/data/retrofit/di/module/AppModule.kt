package com.example.itoken.data.retrofit.di.module

import com.example.itoken.data.AssetRepositoryImpl
import com.example.itoken.data.retrofit.APIService
import com.example.itoken.domain.repository.AssetRepository
import com.example.itoken.domain.usecase.GetAssetsBriefUseCase
import com.example.itoken.domain.usecase.GetAssetsUseCase
import com.example.itoken.utils.DispatcherProvider
import dagger.Module
import dagger.Provides

@Module
class AppModule {

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