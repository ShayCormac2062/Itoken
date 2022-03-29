package com.example.itoken.di.module

import com.example.itoken.utils.DispatcherProvider
import dagger.Module
import dagger.Provides

@Module
class CoroutineModule {

    @Provides
    fun provideScope(): DispatcherProvider = DispatcherProvider()
}
