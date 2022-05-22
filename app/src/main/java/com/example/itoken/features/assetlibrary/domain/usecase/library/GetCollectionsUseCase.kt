package com.example.itoken.features.assetlibrary.domain.usecase.library

import com.example.itoken.features.assetlibrary.domain.repository.CollectionRepository
import javax.inject.Inject

class GetCollectionsUseCase @Inject constructor(
    private val repository: CollectionRepository
) {
    suspend operator fun invoke() = repository.getCollections()
}
