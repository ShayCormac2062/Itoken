package com.example.itoken.domain.enitty

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "showable_assets")
data class ShowableAsset(
    @PrimaryKey
    @ColumnInfo(name = "token_id")
    var tokenId: Int,
    @ColumnInfo(name = "image_preview_url") var imagePreviewUrl: String?,
    @ColumnInfo(name = "image_url") var imageUrl: String?,
    @ColumnInfo(name = "creator_name") var creatorName: String?,
    @ColumnInfo(name = "owner_name") var ownerName: String?,
    @ColumnInfo(name = "token_name") var tokenName: String?,
    @ColumnInfo(name = "price") var price: Int?,
    @ColumnInfo(name = "likes") var likes: Int?,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "address") var address: String?,
)