package com.example.itoken.common.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.itoken.common.di.ViewModelKey
import com.example.itoken.features.addtoken.presentation.viewmodel.AddTokenViewModel
import com.example.itoken.common.viewmodel.CurrentUserViewModel
import com.example.itoken.features.assetlibrary.presentation.viewmodel.AssetsLibraryViewModel
import com.example.itoken.features.assetlibrary.presentation.viewmodel.CollectionViewModel
import com.example.itoken.features.assetlibrary.presentation.viewmodel.SearchAssetViewModel
import com.example.itoken.features.trades.presentation.viewmodel.TradeViewModel
import com.example.itoken.features.trades.presentation.viewmodel.TransactionViewModel
import com.example.itoken.features.user.presentation.viewmodel.AssetViewModel
import com.example.itoken.features.user.presentation.viewmodel.UsersViewModel
import com.example.itoken.utils.MainViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(
        factory: MainViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AssetsLibraryViewModel::class)
    fun bindAssetsLibraryViewModel(
        viewModel: AssetsLibraryViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchAssetViewModel::class)
    fun bindSearchAssetViewModel(
        viewModel: SearchAssetViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddTokenViewModel::class)
    fun bindAddTokenViewModel(
        viewModel: AddTokenViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AssetViewModel::class)
    fun bindAssetViewModel(
        viewModel: AssetViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @Singleton
    @ViewModelKey(UsersViewModel::class)
    fun bindUsersViewModel(
        viewModel: UsersViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CurrentUserViewModel::class)
    fun bindCurrentUserViewModel(
        viewModel: CurrentUserViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TradeViewModel::class)
    fun bindTradeViewModel(
        viewModel: TradeViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TransactionViewModel::class)
    fun bindTransactionViewModel(
        viewModel: TransactionViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CollectionViewModel::class)
    fun bindCollectionViewModel(
        viewModel: CollectionViewModel
    ): ViewModel

}