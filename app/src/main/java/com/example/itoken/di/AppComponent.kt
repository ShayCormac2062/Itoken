package com.example.itoken.di

import com.example.itoken.App
import com.example.itoken.di.module.*
import com.example.itoken.presentation.ui.fragment.AllTokensFragment
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

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: App): Builder

        fun build(): AppComponent

    }
}
