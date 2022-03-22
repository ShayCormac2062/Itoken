package com.example.itoken.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.itoken.data.db.dao.AssetsDao
import com.example.itoken.domain.entity.ShowableAsset

@Database(
    entities = [ShowableAsset::class],
    version = 1,
    exportSchema = true
)
abstract class AssetsDatabase : RoomDatabase() {
    abstract fun assetsDao(): AssetsDao
}