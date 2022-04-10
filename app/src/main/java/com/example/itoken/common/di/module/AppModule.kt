package com.example.itoken.common.di.module

import android.content.Context
import com.example.itoken.App
import com.example.itoken.features.addtoken.data.AddTokenRepositoryImpl
import com.example.itoken.features.addtoken.data.db.AddAssetDao
import com.example.itoken.features.addtoken.domain.repository.AddTokenRepository
import com.example.itoken.features.addtoken.domain.usecase.AddUseCase
import com.example.itoken.features.user.domain.usecase.GetAllCollectedUseCase
import com.example.itoken.features.user.domain.usecase.GetAllCreatedUseCase
import com.example.itoken.features.user.domain.usecase.GetAllFavouritesUseCase
import com.example.itoken.features.assetlibrary.data.AssetRepositoryImpl
import com.example.itoken.features.assetlibrary.data.retrofit.APIService
import com.example.itoken.features.assetlibrary.domain.repository.AssetRepository
import com.example.itoken.features.assetlibrary.domain.usecase.GetAssetsBriefUseCase
import com.example.itoken.features.assetlibrary.domain.usecase.GetAssetsUseCase
import com.example.itoken.features.user.data.AssetsRepositoryImpl
import com.example.itoken.features.user.data.db.dao.AssetsDao
import com.example.itoken.features.user.domain.repository.AssetsRepository
import com.example.itoken.features.user.domain.usecase.GetAllUseCase
import com.example.itoken.utils.DispatcherProvider
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideContext(app: App): Context = app.applicationContext

    @Provides
    fun provideAddTokenRepository(
        addAssetDatabase: AddAssetDao
    ): AddTokenRepository = AddTokenRepositoryImpl(addAssetDatabase)

    @Provides
    fun provideAssetsRepository(
        assetsDatabase: AssetsDao
    ): AssetsRepository = AssetsRepositoryImpl(assetsDatabase)

    @Provides
    fun provideGetAssetsBriefUseCase(
        repository: AssetRepository,
        scope: DispatcherProvider
    ): GetAssetsBriefUseCase = GetAssetsBriefUseCase(repository, scope)

    @Provides
    fun provideGetAssetsUseCase(
        repository: AssetRepository,
        scope: DispatcherProvider
    ): GetAssetsUseCase = GetAssetsUseCase(repository, scope)

    @Provides
    fun provideAssetRepository(
        api: APIService
    ): AssetRepository = AssetRepositoryImpl(api)

    @Provides
    fun provideAddUseCase(
        repository: AddTokenRepository,
        scope: DispatcherProvider
    ): AddUseCase = AddUseCase(repository, scope)

    @Provides
    fun provideGetAllCollectedUseCase(
        repository: AssetsRepository,
        scope: DispatcherProvider
    ): GetAllCollectedUseCase = GetAllCollectedUseCase(repository, scope)

    @Provides
    fun provideGetAllCreatedUseCase(
        repository: AssetsRepository,
        scope: DispatcherProvider
    ): GetAllCreatedUseCase = GetAllCreatedUseCase(repository, scope)

    @Provides
    fun provideGetAllUseCase(
        repository: AssetsRepository,
        scope: DispatcherProvider
    ): GetAllUseCase = GetAllUseCase(repository, scope)

    @Provides
    fun provideGetAllFavouritesUseCase(
        repository: AssetsRepository,
        scope: DispatcherProvider
    ): GetAllFavouritesUseCase = GetAllFavouritesUseCase(repository, scope)
}
