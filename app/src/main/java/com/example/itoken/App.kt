package com.example.itoken

import android.app.Application
import com.example.itoken.di.AppComponent
import com.example.itoken.di.module.AppModule
import com.example.itoken.di.DaggerAppComponent
import com.example.itoken.di.module.NetModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}
