package com.example.itoken.features.assetlibrary.domain.usecase

import com.example.itoken.features.assetlibrary.domain.repository.AssetRepository
import javax.inject.Inject

class GetAssetsBySearchUseCase  @Inject constructor(
    private val repository: AssetRepository
) {

    suspend operator fun invoke(request: String) = repository.getAssetsBySearch(request)

}