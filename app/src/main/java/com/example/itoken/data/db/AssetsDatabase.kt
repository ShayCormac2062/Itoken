package com.example.itoken.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.itoken.data.db.dao.AssetsDao
import com.example.itoken.data.db.entity.DatabaseAsset

@Database(
    entities = [DatabaseAsset::class],
    version = 1,
    exportSchema = true
)
abstract class AssetsDatabase : RoomDatabase() {
    abstract fun assetsDao(): AssetsDao
}
