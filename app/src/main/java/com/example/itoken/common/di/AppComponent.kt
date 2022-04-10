package com.example.itoken.common.di

import com.example.itoken.App
import com.example.itoken.common.di.module.*
import com.example.itoken.features.addtoken.presentation.fragment.AddTokenFragment
import com.example.itoken.features.assetlibrary.presentation.fragment.AllTokensFragment
import com.example.itoken.features.user.presentation.fragment.profile.ProfileFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@[Singleton Component(
    modules = [
        AppModule::class,
        NetModule::class,
        ViewModelModule::class,
        CoroutineModule::class,
        DatabaseModule::class
    ]
)]
interface AppComponent {

    fun inject(fragment: AllTokensFragment)
    fun inject(fragment: AddTokenFragment)
    fun inject(fragment: ProfileFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: App): Builder

        fun build(): AppComponent

    }
}
