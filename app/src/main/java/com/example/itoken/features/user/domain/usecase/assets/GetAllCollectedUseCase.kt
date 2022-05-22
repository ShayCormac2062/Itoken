package com.example.itoken.features.user.domain.usecase.assets

import com.example.itoken.features.user.domain.repository.AssetsRepository
import javax.inject.Inject

class GetAllCollectedUseCase @Inject constructor(
    private val repository: AssetsRepository
) {

    suspend operator fun invoke(name: String) =
        repository.getAllCollected(name)

}