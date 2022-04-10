package com.example.itoken.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.itoken.common.db.model.DatabaseAsset
import com.example.itoken.features.addtoken.data.db.AddAssetDao
import com.example.itoken.features.user.data.db.dao.AssetsDao


@Database(
    entities = [DatabaseAsset::class],
    version = 1,
    exportSchema = true
)
abstract class AssetsDatabase : RoomDatabase() {
    abstract fun assetsDao(): AssetsDao
    abstract fun addAssetDao(): AddAssetDao
}