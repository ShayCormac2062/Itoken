package com.example.itoken.features.trades.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.itoken.features.user.domain.usecase.assets.GetAllTradedUseCase
import javax.inject.Inject

class SharedViewModel @Inject constructor(
    getAllTradedUseCase: GetAllTradedUseCase,
): ViewModel() {

    private var _isTokenSold: MutableLiveData<Boolean?> = MutableLiveData()
    val isTokenSold: LiveData<Boolean?> = _isTokenSold

    init {
        _isTokenSold.value = false
    }

    fun tokenSold(b: Boolean) {
        _isTokenSold.value = b
    }
}