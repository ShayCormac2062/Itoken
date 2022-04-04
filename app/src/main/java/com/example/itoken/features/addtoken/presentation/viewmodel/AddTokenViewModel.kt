package com.example.itoken.features.addtoken.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itoken.features.addtoken.domain.model.AssetModel
import com.example.itoken.features.addtoken.domain.usecase.AddUseCase
import com.example.itoken.features.addtoken.domain.usecase.GetAllCollectedUseCase
import com.example.itoken.features.addtoken.domain.usecase.GetAllCreatedUseCase
import com.example.itoken.features.addtoken.domain.usecase.GetAllFavouritesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddTokenViewModel @Inject constructor(
    private val getAllCollectedUseCase: GetAllCollectedUseCase,
    private val getAllCreatedUseCase: GetAllCreatedUseCase,
    private val getAllFavouritesUseCase: GetAllFavouritesUseCase,
    private val addUseCase: AddUseCase
) : ViewModel() {

    private var _collectedAssetList: MutableLiveData<Result<List<AssetModel>>?> = MutableLiveData()
    val collectedAssetList: LiveData<Result<List<AssetModel>>?> = _collectedAssetList

    private var _createdAssetList: MutableLiveData<Result<List<AssetModel>>?> = MutableLiveData()
    val createdAssetList: LiveData<Result<List<AssetModel>>?> = _createdAssetList

    private var _favouritesAssetList: MutableLiveData<Result<List<AssetModel>>?> = MutableLiveData()
    val favouritesAssetList: LiveData<Result<List<AssetModel>>?> = _favouritesAssetList

    fun add(asset: AssetModel) {
        viewModelScope.launch {
            addUseCase(asset)
        }
    }
}