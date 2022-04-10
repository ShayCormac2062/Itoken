package com.example.itoken

import android.app.Application
import com.example.itoken.common.di.AppComponent
import com.example.itoken.common.di.DaggerAppComponent

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
