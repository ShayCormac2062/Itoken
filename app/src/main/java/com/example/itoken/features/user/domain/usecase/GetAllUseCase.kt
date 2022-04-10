package com.example.itoken.features.user.domain.usecase

import com.example.itoken.features.user.domain.repository.AssetsRepository
import com.example.itoken.utils.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllUseCase @Inject constructor(private val repository: AssetsRepository,
                                        private val scope: DispatcherProvider
) {

    suspend operator fun invoke() =
        withContext(scope.IO) {
            repository.getAll()
        }
}