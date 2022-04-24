package com.example.itoken.features.addtoken.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itoken.features.addtoken.domain.model.AssetModel
import com.example.itoken.features.addtoken.domain.usecase.AddUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddTokenViewModel @Inject constructor(
    private val addUseCase: AddUseCase
) : ViewModel() {

    fun add(asset: AssetModel) {
        viewModelScope.launch {
            addUseCase(asset)
        }
    }
}