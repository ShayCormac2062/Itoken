package com.example.itoken.features.addtoken.domain.usecase

import com.example.itoken.features.addtoken.domain.model.AssetModel
import com.example.itoken.features.addtoken.domain.repository.AddTokenRepository
import javax.inject.Inject

class AddUseCase @Inject constructor(private val repository: AddTokenRepository
) {
    suspend operator fun invoke(asset: AssetModel) = repository.add(asset.toDatabaseAsset())
}