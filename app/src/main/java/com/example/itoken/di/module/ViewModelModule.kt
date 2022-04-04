package com.example.itoken.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.itoken.di.ViewModelKey
import com.example.itoken.features.addtoken.presentation.viewmodel.AddTokenViewModel
import com.example.itoken.features.assetlibrary.presentation.viewmodel.MainViewModel
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
}
