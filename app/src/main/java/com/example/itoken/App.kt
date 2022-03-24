package com.example.itoken

import android.app.Application
import com.example.itoken.data.retrofit.di.AppComponent
import com.example.itoken.data.retrofit.di.module.AppModule
import com.example.itoken.data.retrofit.di.DaggerAppComponent
import com.example.itoken.data.retrofit.di.module.NetModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule())
            .netModule(NetModule())
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}