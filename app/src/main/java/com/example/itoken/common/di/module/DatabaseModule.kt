package com.example.itoken.common.di.module

import android.content.Context
import androidx.room.Room
import com.example.itoken.features.addtoken.data.db.AddAssetDao
import com.example.itoken.common.db.AssetsDatabase
import com.example.itoken.features.user.data.db.dao.AssetsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAssetsDb(context: Context): AssetsDatabase = Room
        .databaseBuilder(context, AssetsDatabase::class.java, "zalupa_drakona")
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideAddAssetDao(db: AssetsDatabase): AddAssetDao = db.addAssetDao()

    @Singleton
    @Provides
    fun provideAssetsDao(db: AssetsDatabase): AssetsDao = db.assetsDao()
}