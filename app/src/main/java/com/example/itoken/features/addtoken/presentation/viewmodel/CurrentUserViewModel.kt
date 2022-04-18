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

//TODO(добавить юскейсы для этой модели)

class CurrentUserViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val changeBalanceUseCase: ChangeBalanceUseCase
): ViewModel() {

    private var _currentUser: MutableLiveData<Result<UserModel?>?> = MutableLiveData()
    val currentUser: LiveData<Result<UserModel?>?> = _currentUser

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

    suspend fun changeBalance(newBalance: Double?) = changeBalanceUseCase(newBalance)
}