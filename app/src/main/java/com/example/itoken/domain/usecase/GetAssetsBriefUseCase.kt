package com.example.itoken.domain.usecase

import com.example.itoken.data.response.Asset
import com.example.itoken.domain.repository.AssetRepository
import com.example.itoken.utils.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAssetsBriefUseCase @Inject constructor(private val repository: AssetRepository,
                                                private val scope: DispatcherProvider) {

    suspend operator fun invoke(): List<Asset> =
        withContext(scope.Main) {
            repository.getAssetsBrief()
        }
}