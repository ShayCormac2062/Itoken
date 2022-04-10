package com.example.itoken.features.addtoken.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itoken.features.addtoken.domain.model.AssetModel
import com.example.itoken.features.addtoken.domain.usecase.AddUseCase
import com.example.itoken.features.user.domain.usecase.GetAllCollectedUseCase
import com.example.itoken.features.user.domain.usecase.GetAllCreatedUseCase
import com.example.itoken.features.user.domain.usecase.GetAllFavouritesUseCase
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