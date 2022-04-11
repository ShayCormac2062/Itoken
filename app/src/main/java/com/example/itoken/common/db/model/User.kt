package com.example.itoken.common.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.itoken.features.user.domain.model.UserModel

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "string_id")
    val stringId: String?,
    @ColumnInfo(name = "image_url")
    val imageUrl: String?,
    @ColumnInfo(name = "nickname")
    val nickname: String?,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "balance")
    val balance: Double?,
) {
    fun toUserModel() = UserModel(
        stringId,
        imageUrl,
        nickname,
        description,
        balance
    )
}