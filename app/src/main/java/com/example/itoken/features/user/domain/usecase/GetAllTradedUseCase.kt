package com.example.itoken.features.user.domain.usecase

import com.example.itoken.features.user.domain.repository.AssetsRepository
import javax.inject.Inject

class GetAllTradedUseCase @Inject constructor(
    private val repository: AssetsRepository
) {

    suspend operator fun invoke(name: String) = repository.getAllTraded(name)
}