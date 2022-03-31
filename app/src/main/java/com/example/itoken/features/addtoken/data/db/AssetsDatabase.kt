package com.example.itoken.features.addtoken.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.itoken.features.addtoken.data.db.dao.AssetsDao
import com.example.itoken.features.addtoken.data.db.entity.DatabaseAsset

@Database(
    entities = [DatabaseAsset::class],
    version = 1,
    exportSchema = true
)
abstract class AssetsDatabase : RoomDatabase() {
    abstract fun assetsDao(): AssetsDao
}
