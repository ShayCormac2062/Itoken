package com.example.itoken.features.addtoken.domain.repository

import com.example.itoken.common.db.model.DatabaseAsset

interface AddTokenRepository {
    suspend fun add(asset: DatabaseAsset)
}