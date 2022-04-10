package com.example.itoken.common.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.itoken.common.di.ViewModelKey
import com.example.itoken.features.addtoken.presentation.viewmodel.AddTokenViewModel
import com.example.itoken.features.assetlibrary.presentation.viewmodel.MainViewModel
import com.example.itoken.features.user.presentation.viewmodel.AssetViewModel
import com.example.itoken.utils.MainViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(
        factory: MainViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(
        viewModel: MainViewModel
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

}