package com.example.itoken.common.db

import androidx.room.TypeConverter
import com.example.itoken.common.db.model.DatabaseAsset
import com.google.gson.Gson

class TypeConverter {

    @TypeConverter
    fun fromString(value: String) = Gson().fromJson(value, Array<DatabaseAsset>::class.java).toList()

    @TypeConverter
    fun fromList(list: List<DatabaseAsset>?) = Gson().toJson(list)
}