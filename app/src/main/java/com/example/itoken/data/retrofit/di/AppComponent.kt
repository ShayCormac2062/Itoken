package com.example.itoken.data.retrofit.di

import com.example.itoken.data.retrofit.di.module.AppModule
import com.example.itoken.data.retrofit.di.module.CoroutineModule
import com.example.itoken.data.retrofit.di.module.NetModule
import com.example.itoken.data.retrofit.di.module.ViewModelModule
import com.example.itoken.presentation.ui.fragment.AllTokensFragment
import dagger.Component

@Component(modules = [AppModule::class, NetModule::class, ViewModelModule::class, CoroutineModule::class])
interface AppComponent {

    fun inject(fragment: AllTokensFragment)
}