package com.example.itoken.features.user.domain.model

import com.example.itoken.common.db.model.User

data class UserModel(
    val stringId: String?,
    val imageUrl: String?,
    val nickname: String?,
    val description: String?,
    var balance: Double?,
) {
    fun toUser() = User(
        0,
        stringId,
        imageUrl,
        nickname,
        description,
        balance
    )
}
