package com.example.itoken.features.addtoken.domain.usecase

import com.example.itoken.features.addtoken.domain.repository.DatabaseRepository
import com.example.itoken.utils.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllFavouritesUseCase @Inject constructor(private val repository: DatabaseRepository,
                                                  private val scope: DispatcherProvider
) {

    suspend operator fun invoke(name: String) =
        withContext(scope.IO) {
            repository.getAllFavourites(name)
        }
}