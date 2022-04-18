package com.example.itoken.features.user.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itoken.features.user.domain.model.ItemAsset
import com.example.itoken.features.user.domain.model.UserModel
import com.example.itoken.features.user.domain.usecase.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class UsersViewModel @Inject constructor(
    private val addUserUseCase: AddUserUseCase,
    private val changeBalanceUseCase: ChangeBalanceUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
): ViewModel() {

    private var _currentUser: MutableLiveData<Result<UserModel?>?> = MutableLiveData()
    val currentUser: LiveData<Result<UserModel?>?> = _currentUser

    suspend fun addUser(user: UserModel) = addUserUseCase(user)

    suspend fun registerUser(user: UserModel) = registerUserUseCase(user)

    suspend fun signOut() = deleteUserUseCase()

    suspend fun changeBalance(newBalance: Double?) = changeBalanceUseCase(newBalance)

    suspend fun getUser() {
        viewModelScope.launch {
            try {
                _currentUser.value = Result.success(getUserUseCase())
                _currentUser.postValue(null)
            } catch (ex: Exception) {
                _currentUser.value = Result.failure(ex)
            }
        }
    }
}