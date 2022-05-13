package com.example.itoken.features.user.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itoken.features.user.domain.model.UserModel
import com.example.itoken.features.user.domain.usecase.user.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class UsersViewModel @Inject constructor(
    private val addUserUseCase: AddUserUseCase,
    private val changeBalanceUseCase: ChangeBalanceUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val changePhotoUseCase: ChangePhotoUseCase,
) : ViewModel() {

    private var _currentUser: MutableLiveData<UserModel?> = MutableLiveData()
    val currentUser: LiveData<UserModel?> = _currentUser

    private var _isUserExists: MutableLiveData<Boolean?> = MutableLiveData()
    val isUserExists: LiveData<Boolean?> = _isUserExists

    fun addUser(user: UserModel) {
        viewModelScope.launch {
            _currentUser.value = addUserUseCase(user)
        }
    }

    fun registerUser(user: UserModel) {
        viewModelScope.launch {
            _isUserExists.value = registerUserUseCase(user)
        }
    }

    fun signOut() {
        viewModelScope.launch {
            deleteUserUseCase()
            _currentUser.value = null
        }
    }

    fun changeBalance(newBalance: Double?) {
        viewModelScope.launch {
            changeBalanceUseCase(newBalance)
        }
    }

    fun changePhoto(url: String) {
        viewModelScope.launch {
            changePhotoUseCase(url)
        }
    }

    fun getUser() {
        viewModelScope.launch {
            try {
                _currentUser.value = getUserUseCase()
            } catch (ex: Exception) {
                _currentUser.value = null
            }
        }
    }

}