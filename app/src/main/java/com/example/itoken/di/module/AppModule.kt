package com.example.itoken.di.module

import android.content.Context
import com.example.itoken.App
import com.example.itoken.features.addtoken.data.DatabaseRepositoryImpl
import com.example.itoken.features.addtoken.data.db.AssetsDatabase
import com.example.itoken.features.addtoken.data.db.dao.AssetsDao
import com.example.itoken.features.addtoken.domain.repository.DatabaseRepository
import com.example.itoken.features.addtoken.domain.usecase.AddUseCase
import com.example.itoken.features.addtoken.domain.usecase.GetAllCollectedUseCase
import com.example.itoken.features.addtoken.domain.usecase.GetAllCreatedUseCase
import com.example.itoken.features.addtoken.domain.usecase.GetAllFavouritesUseCase
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
        repository: DatabaseRepository,
        scope: DispatcherProvider
    ): AddUseCase = AddUseCase(repository, scope)

    @Provides
    fun provideGetAllCollectedUseCase(
        repository: DatabaseRepository,
        scope: DispatcherProvider
    ): GetAllCollectedUseCase = GetAllCollectedUseCase(repository, scope)

    @Provides
    fun provideGetAllCreated(
        repository: DatabaseRepository,
        scope: DispatcherProvider
    ): GetAllCreatedUseCase = GetAllCreatedUseCase(repository, scope)

    @Provides
    fun provideGetAllFavouritesUseCase(
        repository: DatabaseRepository,
        scope: DispatcherProvider
    ): GetAllFavouritesUseCase = GetAllFavouritesUseCase(repository, scope)

    @Provides
    fun provideDatabaseRepository(
        assetsDatabase: AssetsDao
    ): DatabaseRepository = DatabaseRepositoryImpl(assetsDatabase)
}
