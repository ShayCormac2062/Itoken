package com.example.itoken.features.user.domain.usecase.user

import com.example.itoken.features.user.domain.repository.UsersRepository
import javax.inject.Inject

class ChangePhotoUseCase @Inject constructor(
    private val repository: UsersRepository
) {

    suspend operator fun invoke(newPhoto: String?) = repository.changePhoto(newPhoto)

}