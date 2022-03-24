package com.example.itoken.data.retrofit.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.itoken.data.retrofit.di.ViewModelKey
import com.example.itoken.presentation.viewmodel.MainViewModel
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
}