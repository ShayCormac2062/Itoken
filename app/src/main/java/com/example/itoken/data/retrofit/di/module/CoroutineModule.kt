package com.example.itoken.data.retrofit.di.module

import com.example.itoken.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class CoroutineModule {

    @Provides
    fun provideScope(): DispatcherProvider = DispatcherProvider()
}