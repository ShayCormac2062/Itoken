package com.example.itoken.features.addtoken.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itoken.features.user.domain.model.UserModel
import com.example.itoken.features.user.domain.usecase.ChangeBalanceUseCase
import com.example.itoken.features.user.domain.usecase.GetUserUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CurrentUserViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val changeBalanceUseCase: ChangeBalanceUseCase
): ViewModel() {

    private var _currentUser: MutableLiveData<UserModel?> = MutableLiveData()
    val currentUser: LiveData<UserModel?> = _currentUser

    suspend fun getUser() {
        viewModelScope.launch {
            try {
                _currentUser.value = getUserUseCase()
                _currentUser.postValue(null)
            } catch (ex: Exception) {
                _currentUser.value = null
            }
        }
    }

    suspend fun changeBalance(newBalance: Double?) = changeBalanceUseCase(newBalance)
}