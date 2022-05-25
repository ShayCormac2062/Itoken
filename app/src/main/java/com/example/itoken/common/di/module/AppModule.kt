package com.example.itoken.common.di.module

import android.content.Context
import com.example.itoken.App
import com.example.itoken.common.db.dao.GetUserDao
import com.example.itoken.features.addtoken.data.AddTokenRepositoryImpl
import com.example.itoken.common.db.dao.AddAssetDao
import com.example.itoken.features.addtoken.domain.repository.AddTokenRepository
import com.example.itoken.features.addtoken.domain.usecase.AddUseCase
import com.example.itoken.features.assetlibrary.data.repository.AssetRepositoryImpl
import com.example.itoken.features.assetlibrary.data.retrofit.APIService
import com.example.itoken.features.assetlibrary.domain.repository.AssetRepository
import com.example.itoken.common.traderepository.CreateTradeRepositoryImpl
import com.example.itoken.features.trades.data.repository.TradeRepositoryImpl
import com.example.itoken.common.traderepository.CreateTradeRepository
import com.example.itoken.features.assetlibrary.data.repository.CollectionRepositoryImpl
import com.example.itoken.features.assetlibrary.domain.repository.CollectionRepository
import com.example.itoken.features.assetlibrary.domain.usecase.library.GetAssetsBriefUseCase
import com.example.itoken.features.assetlibrary.domain.usecase.library.GetAssetsCheapUseCase
import com.example.itoken.features.assetlibrary.domain.usecase.library.GetCollectionsUseCase
import com.example.itoken.features.assetlibrary.domain.usecase.search.GetAssetsByCategoryUseCase
import com.example.itoken.features.assetlibrary.domain.usecase.search.GetAssetsBySearchUseCase
import com.example.itoken.features.assetlibrary.domain.usecase.search.GetAssetsUseCase
import com.example.itoken.features.trades.data.repository.TransactionRepositoryImpl
import com.example.itoken.features.trades.domain.repository.TradeRepository
import com.example.itoken.features.trades.domain.repository.TransactionRepository
import com.example.itoken.features.trades.domain.usecase.*
import com.example.itoken.features.user.data.AssetsRepositoryImpl
import com.example.itoken.features.user.data.UsersRepositoryImpl
import com.example.itoken.features.user.data.db.dao.AssetsDao
import com.example.itoken.features.user.data.db.dao.UsersDao
import com.example.itoken.features.user.domain.repository.AssetsRepository
import com.example.itoken.features.user.domain.repository.UsersRepository
import com.example.itoken.features.user.domain.usecase.assets.*
import com.example.itoken.features.user.domain.usecase.user.*
import com.example.itoken.utils.DispatcherProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideContext(app: App): Context = app.applicationContext

    @Provides
    fun provideAddTokenRepository(
        addAssetDatabase: AddAssetDao,
        storage: DatabaseReference,
        scope: DispatcherProvider
    ): AddTokenRepository = AddTokenRepositoryImpl(addAssetDatabase, storage, scope)

    @Provides
    fun provideAssetsRepository(
        assetsDatabase: AssetsDao,
        addAssetDatabase: AddAssetDao,
        scope: DispatcherProvider,
        ref: DatabaseReference
    ): AssetsRepository = AssetsRepositoryImpl(assetsDatabase, addAssetDatabase, scope, ref)

    @Provides
    fun provideUsersRepository(
        usersDatabase: UsersDao,
        getUserDatabase: GetUserDao,
        firebase: DatabaseReference,
        storage: FirebaseStorage,
        scope: DispatcherProvider
    ): UsersRepository = UsersRepositoryImpl(usersDatabase, getUserDatabase, firebase, storage, scope)

    @Provides
    fun provideTransactionRepository(
        firebase: DatabaseReference,
        db: GetUserDao
    ): TransactionRepository = TransactionRepositoryImpl(firebase, db)

    @Provides
    fun provideCollectionRepository(
        api: APIService
    ): CollectionRepository = CollectionRepositoryImpl(api)

    @Provides
    fun provideGetAssetsBriefUseCase(
        repository: AssetRepository,
    ): GetAssetsBriefUseCase = GetAssetsBriefUseCase(repository)

    @Provides
    fun provideGetAssetsUseCase(
        repository: AssetRepository,
    ): GetAssetsUseCase = GetAssetsUseCase(repository)

    @Provides
    fun provideAssetRepository(
        api: APIService,
    ): AssetRepository = AssetRepositoryImpl(api)

    @Provides
    fun provideTradeRepository(
        ref: DatabaseReference
    ): TradeRepository = TradeRepositoryImpl(ref)

    @Provides
    fun provideCreateTradeRepository(
        storage: FirebaseStorage,
        ref: DatabaseReference
    ): CreateTradeRepository = CreateTradeRepositoryImpl(ref, storage)

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
    fun provideGetAssetsByCategoryUseCase(
        repository: AssetRepository,
    ): GetAssetsByCategoryUseCase = GetAssetsByCategoryUseCase(repository)

    @Provides
    fun provideGetAssetsBySearchUseCase(
        repository: AssetRepository,
    ): GetAssetsBySearchUseCase = GetAssetsBySearchUseCase(repository)

    @Provides
    fun provideGetAssetsCheapUseCase(
        repository: AssetRepository,
    ): GetAssetsCheapUseCase = GetAssetsCheapUseCase(repository)

    @Provides
    fun provideGetAllFavouritesUseCase(
        repository: AssetsRepository,
    ): GetAllTradedUseCase = GetAllTradedUseCase(repository)

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

    @Provides
    fun provideChangePhotoUseCase(
        repository: UsersRepository
    ): ChangePhotoUseCase = ChangePhotoUseCase(repository)

    @Provides
    fun provideCreateTradeUseCase(
        repository: CreateTradeRepository
    ): CreateTradeUseCase = CreateTradeUseCase(repository)


    @Provides
    fun provideGetAllTradesUseCase(
        repository: TradeRepository
    ): GetAllTradesUseCase = GetAllTradesUseCase(repository)

    @Provides
    fun provideSendTokenToUserUseCase(
        repository: TransactionRepository
    ): SendTokenToUserUseCase = SendTokenToUserUseCase(repository)

    @Provides
    fun provideCloseTradeUseCase(
        repository: TransactionRepository
    ): CloseTradeUseCase = CloseTradeUseCase(repository)

    @Provides
    fun provideChangeMemberListUseCase(
        repository: TransactionRepository
    ): ChangeMemberListUseCase = ChangeMemberListUseCase(repository)

    @Provides
    fun provideGetCollectionsUseCase(
        repository: CollectionRepository
    ): GetCollectionsUseCase = GetCollectionsUseCase(repository)

    @Provides
    fun provideTransferMoneyToBarkerUseCase(
        repository: TransactionRepository
    ): TransferMoneyToBarkerUseCase = TransferMoneyToBarkerUseCase(repository)

}
