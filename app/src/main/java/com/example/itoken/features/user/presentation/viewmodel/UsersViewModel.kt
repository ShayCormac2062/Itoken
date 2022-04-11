package com.example.itoken.features.user.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.itoken.features.user.domain.model.UserModel
import com.example.itoken.features.user.domain.usecase.AddUserUseCase
import com.example.itoken.features.user.domain.usecase.ChangeBalanceUseCase
import com.example.itoken.features.user.domain.usecase.DeleteUserUseCase
import javax.inject.Inject

class UsersViewModel @Inject constructor(
    private val addUserUseCase: AddUserUseCase,
    private val changeBalanceUseCase: ChangeBalanceUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
): ViewModel() {

    suspend fun addUser(user: UserModel) = addUserUseCase(user)

    suspend fun signOut() = deleteUserUseCase()

    suspend fun changeBalance(newBalance: Double?) = changeBalanceUseCase(newBalance)
}