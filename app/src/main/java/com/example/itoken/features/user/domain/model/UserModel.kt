package com.example.itoken.features.user.domain.model

import com.example.itoken.common.db.model.DatabaseAsset
import com.example.itoken.common.db.model.User
import java.io.Serializable

data class UserModel(
    val stringId: String?,
    var imageUrl: String?,
    val nickname: String?,
    val description: String?,
    val password: String?,
    val email: String?,
    var assets: List<ItemAsset>?,
    var balance: Double?,
) {
    fun toUser() = User(
        0,
        stringId,
        imageUrl,
        nickname,
        description,
        password,
        email,
        changeList(assets),
        balance
    )

    private fun changeList(assets: List<ItemAsset>?): List<DatabaseAsset> {
        val result = arrayListOf<DatabaseAsset>()
        assets?.forEach {
            result.add(it.toDatabaseAsset())
        }
        return result
    }
}
