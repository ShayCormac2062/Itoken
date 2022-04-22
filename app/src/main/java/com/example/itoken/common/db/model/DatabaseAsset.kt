package com.example.itoken.common.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.itoken.features.user.domain.model.ItemAsset

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
    @ColumnInfo(name = "price") var price: Long?,
    @ColumnInfo(name = "likes") var likes: Long?,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "address") var address: String?,
) {

    fun toItemAsset() = ItemAsset(
        tokenId.toString(),
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
