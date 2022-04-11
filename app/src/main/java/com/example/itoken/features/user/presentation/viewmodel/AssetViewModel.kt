package com.example.itoken.features.user.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itoken.features.assetlibrary.domain.model.InfoAsset
import com.example.itoken.features.user.domain.model.ItemAsset
import com.example.itoken.features.user.domain.usecase.GetAllCollectedUseCase
import com.example.itoken.features.user.domain.usecase.GetAllCreatedUseCase
import com.example.itoken.features.user.domain.usecase.GetAllFavouritesUseCase
import com.example.itoken.features.user.domain.usecase.GetAllUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class AssetViewModel @Inject constructor(
    private val getAllCollectedUseCase: GetAllCollectedUseCase,
    private val getAllCreatedUseCase: GetAllCreatedUseCase,
    private val getAllFavouritesUseCase: GetAllFavouritesUseCase,
    private val getAllUseCase: GetAllUseCase,
) : ViewModel() {

    private var _collectedAssetList: MutableLiveData<Result<List<ItemAsset>>?> = MutableLiveData()
    val collectedAssetList: LiveData<Result<List<ItemAsset>>?> = _collectedAssetList

    private var _createdAssetList: MutableLiveData<Result<List<ItemAsset>>?> = MutableLiveData()
    val createdAssetList: LiveData<Result<List<ItemAsset>>?> = _createdAssetList

    private var _favouritesAssetList: MutableLiveData<Result<List<ItemAsset>>?> = MutableLiveData()
    val favouritesAssetList: LiveData<Result<List<ItemAsset>>?> = _favouritesAssetList

    private var _allAssetList: MutableLiveData<Result<List<ItemAsset>>?> = MutableLiveData()
    val allAssetList: LiveData<Result<List<ItemAsset>>?> = _allAssetList

    suspend fun getCollected(name: String) {
        viewModelScope.launch {
            try {
                val assetList = arrayListOf<ItemAsset>()
                getAllCollectedUseCase(name).forEach(action = {
                    assetList.add(it)
                })
                _collectedAssetList.value = Result.success(assetList)
            } catch (ex: Exception) {
                _collectedAssetList.value = Result.failure(ex)
            }
        }
    }

    suspend fun getCreated(name: String) {
        viewModelScope.launch {
            try {
                val assetList = arrayListOf<ItemAsset>()
                getAllCreatedUseCase(name).forEach(action = {
                    assetList.add(it)
                })
                _createdAssetList.value = Result.success(assetList)
            } catch (ex: Exception) {
                _createdAssetList.value = Result.failure(ex)
            }
        }
    }

    suspend fun getFavourites(name: String) {
        viewModelScope.launch {
            try {
                val assetList = arrayListOf<ItemAsset>()
                getAllFavouritesUseCase(name).forEach(action = {
                    assetList.add(it)
                })
                _favouritesAssetList.value = Result.success(assetList)
            } catch (ex: Exception) {
                _favouritesAssetList.value = Result.failure(ex)
            }
        }
    }

    suspend fun getAll() {
        viewModelScope.launch {
            try {
                val assetList = arrayListOf<ItemAsset>()
                getAllUseCase().forEach(action = {
                    assetList.add(it)
                })
                _allAssetList.value = Result.success(assetList)
            } catch (ex: Exception) {
                _allAssetList.value = Result.failure(ex)
            }
        }
    }
}