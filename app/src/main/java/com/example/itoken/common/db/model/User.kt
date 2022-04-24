package com.example.itoken.common.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.itoken.features.user.domain.model.ItemAsset
import com.example.itoken.features.user.domain.model.UserModel
import java.io.Serializable

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
    @ColumnInfo(name = "password")
    val password: String?,
    @ColumnInfo(name = "email")
    val email: String?,
    @ColumnInfo(name = "assets")
    val assets: List<DatabaseAsset>?,
    @ColumnInfo(name = "balance")
    val balance: Double?,
) : Serializable {
    fun toUserModel() = UserModel(
        stringId,
        imageUrl,
        nickname,
        description,
        password,
        email,
        changeList(assets),
        balance
    )

    private fun changeList(assets: List<DatabaseAsset>?): List<ItemAsset> {
        val result = arrayListOf<ItemAsset>()
        assets?.forEach {
            result.add(it.toItemAsset())
        }
        return result
    }
}