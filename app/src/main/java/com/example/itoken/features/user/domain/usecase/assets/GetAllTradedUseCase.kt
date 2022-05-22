package com.example.itoken.features.user.domain.usecase.assets

import com.example.itoken.features.user.domain.repository.AssetsRepository
import javax.inject.Inject

class GetAllTradedUseCase @Inject constructor(
    private val repository: AssetsRepository
) {

    suspend operator fun invoke(name: String, userId: String?) =
        repository.getAllTraded(name, userId)

}
