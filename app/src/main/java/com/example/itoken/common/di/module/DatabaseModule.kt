package com.example.itoken.common.di.module

import android.content.Context
import androidx.room.Room
import com.example.itoken.common.db.dao.AddAssetDao
import com.example.itoken.common.db.AssetsDatabase
import com.example.itoken.common.db.UserDatabase
import com.example.itoken.common.db.dao.GetUserDao
import com.example.itoken.features.user.data.db.dao.AssetsDao
import com.example.itoken.features.user.data.db.dao.UsersDao
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
    fun provideUsersDb(context: Context): UserDatabase = Room
        .databaseBuilder(context, UserDatabase::class.java, "chlen_mamonta")
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideAddAssetDao(db: AssetsDatabase): AddAssetDao = db.addAssetDao()

    @Singleton
    @Provides
    fun provideAssetsDao(db: AssetsDatabase): AssetsDao = db.assetsDao()

    @Singleton
    @Provides
    fun provideUsersDao(db: UserDatabase): UsersDao = db.usersDao()

    @Singleton
    @Provides
    fun provideAddUserDao(db: UserDatabase): GetUserDao = db.getUserDao()
}