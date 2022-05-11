package com.example.itoken.features.assetlibrary.domain.model

import com.example.itoken.features.assetlibrary.presentation.model.CollectionBrief
import java.io.Serializable

data class InfoCollection(
    val bannerImageUrl: String?,
    val createdDate: String?,
    val description: String?,
    val imageUrl: String?,
    val name: String?,
    val averagePrice: Int?,
    val count: Int?,
    val username: String?,
) : Serializable {

    fun toCollectionBrief() = CollectionBrief(
        bannerImageUrl,
        name,
        username
    )

}