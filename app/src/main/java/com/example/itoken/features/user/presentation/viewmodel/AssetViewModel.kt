package com.example.itoken.features.user.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itoken.common.db.model.DatabaseAsset
import com.example.itoken.features.user.domain.model.ItemAsset
import com.example.itoken.features.user.domain.usecase.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class AssetViewModel @Inject constructor(
    private val getAllCollectedUseCase: GetAllCollectedUseCase,
    private val getAllCreatedUseCase: GetAllCreatedUseCase,
    private val getAllFavouritesUseCase: GetAllFavouritesUseCase,
    private val getAllUseCase: GetAllUseCase,
    private val addAllUseCase: AddAllUseCase,
) : ViewModel() {

    private var _collectedAssetList: MutableLiveData<Result<List<ItemAsset>>?> = MutableLiveData()
    val collectedAssetList: LiveData<Result<List<ItemAsset>>?> = _collectedAssetList

    private var _createdAssetList: MutableLiveData<Result<List<ItemAsset>>?> = MutableLiveData()
    val createdAssetList: LiveData<Result<List<ItemAsset>>?> = _createdAssetList

    private var _favouritesAssetList: MutableLiveData<Result<List<ItemAsset>>?> = MutableLiveData()
    val favouritesAssetList: LiveData<Result<List<ItemAsset>>?> = _favouritesAssetList

    private var _allAssetList: MutableLiveData<Result<List<ItemAsset>>?> = MutableLiveData()
    val allAssetList: LiveData<Result<List<ItemAsset>>?> = _allAssetList

    private var _collectedAssetListAmount: MutableLiveData<Result<Int>> = MutableLiveData()
    val collectedAssetListAmount: LiveData<Result<Int>> = _collectedAssetListAmount

    private var _createdAssetListAmount: MutableLiveData<Result<Int>> = MutableLiveData()
    val createdAssetListAmount: LiveData<Result<Int>> = _createdAssetListAmount

    private var _favouritesAssetListAmount: MutableLiveData<Result<Int>> = MutableLiveData()
    val favouritesAssetListAmount: LiveData<Result<Int>> = _favouritesAssetListAmount

    private var _allAssetListAmount: MutableLiveData<Result<Int>> = MutableLiveData()
    val allAssetListAmount: LiveData<Result<Int>> = _allAssetListAmount

    suspend fun getCollected(name: String) {
        viewModelScope.launch {
            try {
                val assetList = arrayListOf<ItemAsset>()
                getAllCollectedUseCase(name).forEach(action = {
                    assetList.add(it)
                })
                _collectedAssetList.value = Result.success(assetList)
                _collectedAssetList.postValue(null)
            } catch (ex: Exception) {
                _collectedAssetList.value = Result.failure(ex)
            }
        }
    }

    suspend fun getCollectedAmount(name: String) {
        viewModelScope.launch {
            try {
                _collectedAssetListAmount.value = Result.success(getAllCollectedUseCase(name).size)
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
                _createdAssetList.postValue(null)
            } catch (ex: Exception) {
                _createdAssetList.value = Result.failure(ex)
            }
        }
    }

    suspend fun getCreatedAmount(name: String) {
        viewModelScope.launch {
            try {
                _createdAssetListAmount.value = Result.success(getAllCreatedUseCase(name).size)
            } catch (ex: Exception) {
                _collectedAssetList.value = Result.failure(ex)
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
                _favouritesAssetList.postValue(null)
            } catch (ex: Exception) {
                _favouritesAssetList.value = Result.failure(ex)
            }
        }
    }

    suspend fun getFavouritesAmount(name: String) {
        viewModelScope.launch {
            try {
                _favouritesAssetListAmount.value = Result.success(getAllFavouritesUseCase(name).size)
            } catch (ex: Exception) {
                _collectedAssetList.value = Result.failure(ex)
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

    suspend fun addAll(assets: List<ItemAsset>) {
        viewModelScope.launch {
            addAllUseCase(assets)
        }
    }
}