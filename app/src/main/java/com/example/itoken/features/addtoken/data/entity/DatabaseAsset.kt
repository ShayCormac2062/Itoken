package com.example.itoken.features.addtoken.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.itoken.features.addtoken.domain.model.AssetModel

@Entity(tableName = "database_assets")
data class DatabaseAsset(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "token_id")
    var tokenId: Long,
    @ColumnInfo(name = "image_preview_url") var imagePreviewUrl: String?,
    @ColumnInfo(name = "image_url") var imageUrl: String?,
    @ColumnInfo(name = "creator_name") var creatorName: String?,
    @ColumnInfo(name = "owner_name") var ownerName: String?,
    @ColumnInfo(name = "token_name") var tokenName: String?,
    @ColumnInfo(name = "price") var price: Int?,
    @ColumnInfo(name = "likes") var likes: Int?,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "address") var address: String?,
) {
    fun toAssetDto() = AssetModel(
        tokenId,
        imagePreviewUrl,
        imageUrl,
        creatorName,
        ownerName,
        tokenName,
        price,
        likes,
        description,
        address
    )
}
