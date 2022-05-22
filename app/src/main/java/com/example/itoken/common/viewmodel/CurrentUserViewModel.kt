package com.example.itoken.common.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itoken.features.user.domain.model.UserModel
import com.example.itoken.features.user.domain.usecase.user.ChangeBalanceUseCase
import com.example.itoken.features.user.domain.usecase.user.GetUserUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CurrentUserViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val changeBalanceUseCase: ChangeBalanceUseCase
): ViewModel() {

    private var _currentUser: MutableLiveData<UserModel?> = MutableLiveData()
    val currentUser: LiveData<UserModel?> = _currentUser

    fun getUser() {
        viewModelScope.launch {
            try {
                _currentUser.value = getUserUseCase()
            } catch (ex: Exception) {
                _currentUser.value = null
            }
        }
    }

    fun changeBalance(newBalance: Double?) {
        viewModelScope.launch {
            changeBalanceUseCase(newBalance)
        }
    }
}