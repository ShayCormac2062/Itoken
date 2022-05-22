package com.example.itoken.features.assetlibrary.domain.usecase.library

import com.example.itoken.features.assetlibrary.domain.repository.AssetRepository
import javax.inject.Inject

class GetAssetsBriefUseCase @Inject constructor(
    private val repository: AssetRepository
) {

    suspend operator fun invoke() =
        repository.getAssetsBrief()

}
