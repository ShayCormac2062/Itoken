package com.example.itoken.features.assetlibrary.domain.usecase.search

import com.example.itoken.features.assetlibrary.domain.repository.AssetRepository
import javax.inject.Inject

class GetAssetsUseCase @Inject constructor(
    private val repository: AssetRepository,
) {

    suspend operator fun invoke() = repository.getAssets()
}
