package com.example.itoken.features.user.domain.usecase

import com.example.itoken.features.user.domain.model.ItemAsset
import com.example.itoken.features.user.domain.repository.AssetsRepository
import javax.inject.Inject

class AddAllUseCase @Inject constructor(
    private val repository: AssetsRepository
) {

    suspend operator fun invoke(assets: List<ItemAsset>) = repository.addAll(assets)

}