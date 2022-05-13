package com.example.itoken.features.user.domain.usecase.assets

import com.example.itoken.features.addtoken.domain.repository.AddTokenRepository
import com.example.itoken.features.user.domain.repository.AssetsRepository
import com.example.itoken.utils.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllCreatedUseCase @Inject constructor(
    private val repository: AssetsRepository
) {

    suspend operator fun invoke(name: String) = repository.getAllCreated(name)

}