package com.example.itoken.features.assetlibrary.domain.usecase

import com.example.itoken.features.assetlibrary.domain.repository.AssetRepository
import com.example.itoken.utils.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAssetsUseCase @Inject constructor(
    private val repository: AssetRepository,
    private val scope: DispatcherProvider
) {

    suspend operator fun invoke() = repository.getAssets()
}
