package com.example.itoken.features.assetlibrary.domain.usecase

import com.example.itoken.features.assetlibrary.domain.repository.AssetRepository
import javax.inject.Inject

class GetAssetsByCategoryUseCase @Inject constructor(
    private val repository: AssetRepository
) {

    suspend operator fun invoke(category: String) = repository.getAssetsByCategory(category)

}