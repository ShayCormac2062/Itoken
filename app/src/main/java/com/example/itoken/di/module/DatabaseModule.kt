package com.example.itoken.di.module

import android.content.Context
import androidx.room.Room
import com.example.itoken.features.addtoken.data.db.AssetsDatabase
import com.example.itoken.features.addtoken.data.db.dao.AssetsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDb(context: Context): AssetsDatabase = Room
        .databaseBuilder(context, AssetsDatabase::class.java, "dao")
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideAssetDao(db: AssetsDatabase): AssetsDao = db.assetsDao()
}