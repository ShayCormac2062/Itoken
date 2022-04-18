package com.example.itoken.common.di.module

import android.content.Context
import com.example.itoken.App
import com.example.itoken.common.db.dao.GetUserDao
import com.example.itoken.features.addtoken.data.AddTokenRepositoryImpl
import com.example.itoken.common.db.dao.AddAssetDao
import com.example.itoken.features.addtoken.domain.repository.AddTokenRepository
import com.example.itoken.features.addtoken.domain.usecase.AddUseCase
import com.example.itoken.features.assetlibrary.data.AssetRepositoryImpl
import com.example.itoken.features.assetlibrary.data.retrofit.APIService
import com.example.itoken.features.assetlibrary.domain.repository.AssetRepository
import com.example.itoken.features.assetlibrary.domain.usecase.GetAssetsBriefUseCase
import com.example.itoken.features.assetlibrary.domain.usecase.GetAssetsUseCase
import com.example.itoken.features.user.data.AssetsRepositoryImpl
import com.example.itoken.features.user.data.UsersRepositoryImpl
import com.example.itoken.features.user.data.db.dao.AssetsDao
import com.example.itoken.features.user.data.db.dao.UsersDao
import com.example.itoken.features.user.domain.repository.AssetsRepository
import com.example.itoken.features.user.domain.repository.UsersRepository
import com.example.itoken.features.user.domain.usecase.*
import com.example.itoken.utils.DispatcherProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideContext(app: App): Context = app.applicationContext

    @Provides
    fun provideAddTokenRepository(
        addAssetDatabase: AddAssetDao,
        scope: DispatcherProvider
    ): AddTokenRepository = AddTokenRepositoryImpl(addAssetDatabase, scope)

    @Provides
    fun provideAssetsRepository(
        assetsDatabase: AssetsDao,
        addAssetDatabase: AddAssetDao,
        firebase: DatabaseReference,
        scope: DispatcherProvider
    ): AssetsRepository = AssetsRepositoryImpl(assetsDatabase, addAssetDatabase, firebase, scope)

    @Provides
    fun provideUsersRepository(
        usersDatabase: UsersDao,
        getUserDatabase: GetUserDao,
        firebase: DatabaseReference,
        scope: DispatcherProvider
    ): UsersRepository = UsersRepositoryImpl(usersDatabase, getUserDatabase, firebase, scope)

    @Provides
    fun provideGetAssetsBriefUseCase(
        repository: AssetRepository,
    ): GetAssetsBriefUseCase = GetAssetsBriefUseCase(repository)

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
        repository: AddTokenRepository
    ): AddUseCase = AddUseCase(repository)

    @Provides
    fun provideGetAllCollectedUseCase(
        repository: AssetsRepository,
    ): GetAllCollectedUseCase = GetAllCollectedUseCase(repository)

    @Provides
    fun provideGetAllCreatedUseCase(
        repository: AssetsRepository,
    ): GetAllCreatedUseCase = GetAllCreatedUseCase(repository)

    @Provides
    fun provideGetAllUseCase(
        repository: AssetsRepository,
    ): GetAllUseCase = GetAllUseCase(repository)

    @Provides
    fun provideGetAllFavouritesUseCase(
        repository: AssetsRepository,
    ): GetAllFavouritesUseCase = GetAllFavouritesUseCase(repository)

    @Provides
    fun provideAddUserUseCase(
        repository: UsersRepository
    ): AddUserUseCase = AddUserUseCase(repository)

    @Provides
    fun provideChangeBalanceUseCase(
        repository: UsersRepository
    ): ChangeBalanceUseCase = ChangeBalanceUseCase(repository)

    @Provides
    fun provideDeleteUserUseCase(
        repository: UsersRepository
    ): DeleteUserUseCase = DeleteUserUseCase(repository)

    @Provides
    fun provideGetUserUseCase(
        repository: UsersRepository
    ): GetUserUseCase = GetUserUseCase(repository)

    @Provides
    fun provideAddAssetUseCase(
        repository: AssetsRepository
    ): AddAllUseCase = AddAllUseCase(repository)

    @Provides
    fun provideRegisterUserUseCase(
        repository: UsersRepository
    ): RegisterUserUseCase = RegisterUserUseCase(repository)
}
