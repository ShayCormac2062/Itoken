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

    private var _collectedAssetList: MutableLiveData<List<ItemAsset>?> = MutableLiveData()
    val collectedAssetList: LiveData<List<ItemAsset>?> = _collectedAssetList

    private var _createdAssetList: MutableLiveData<List<ItemAsset>?> = MutableLiveData()
    val createdAssetList: LiveData<List<ItemAsset>?> = _createdAssetList

    private var _favouritesAssetList: MutableLiveData<List<ItemAsset>?> = MutableLiveData()
    val favouritesAssetList: LiveData<List<ItemAsset>?> = _favouritesAssetList

    private var _allAssetList: MutableLiveData<List<ItemAsset>?> = MutableLiveData()
    val allAssetList: LiveData<List<ItemAsset>?> = _allAssetList

    private var _collectedAssetListAmount: MutableLiveData<Int> = MutableLiveData()
    val collectedAssetListAmount: LiveData<Int> = _collectedAssetListAmount

    private var _createdAssetListAmount: MutableLiveData<Int> = MutableLiveData()
    val createdAssetListAmount: LiveData<Int> = _createdAssetListAmount

    private var _favouritesAssetListAmount: MutableLiveData<Int> = MutableLiveData()
    val favouritesAssetListAmount: LiveData<Int> = _favouritesAssetListAmount

    private var _allAssetListAmount: MutableLiveData<Int> = MutableLiveData()
    val allAssetListAmount: LiveData<Int> = _allAssetListAmount

    suspend fun getCollected(name: String) {
        viewModelScope.launch {
            try {
                val assetList = arrayListOf<ItemAsset>()
                getAllCollectedUseCase(name).forEach(action = {
                    assetList.add(it)
                })
                _collectedAssetList.value = assetList
            } catch (ex: Exception) {
                _collectedAssetList.value = null
            }
        }
    }

    suspend fun getCollectedAmount(name: String) {
        viewModelScope.launch {
            try {
                _collectedAssetListAmount.value = getAllCollectedUseCase(name).size
            } catch (ex: Exception) {
                _collectedAssetListAmount.value = 0
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
                _createdAssetList.value = assetList
            } catch (ex: Exception) {
                _createdAssetList.value = null
            }
        }
    }

    suspend fun getCreatedAmount(name: String) {
        viewModelScope.launch {
            try {
                _createdAssetListAmount.value = getAllCreatedUseCase(name).size
            } catch (ex: Exception) {
                _collectedAssetListAmount.value = 0
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
                _favouritesAssetList.value = assetList
            } catch (ex: Exception) {
                _favouritesAssetList.value = null
            }
        }
    }

    suspend fun getFavouritesAmount(name: String) {
        viewModelScope.launch {
            try {
                _favouritesAssetListAmount.value = getAllFavouritesUseCase(name).size
            } catch (ex: Exception) {
                _collectedAssetListAmount.value = 0
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
                _allAssetList.value = assetList
            } catch (ex: Exception) {
                _allAssetList.value = null
            }
        }
    }

    suspend fun addAll(assets: List<ItemAsset>) {
        viewModelScope.launch {
            addAllUseCase(assets)
        }
    }

    fun closePage() {
        _collectedAssetList.value = null
        _createdAssetList.value = null
        _favouritesAssetList.value = null
        _collectedAssetListAmount.value = 0
        _createdAssetListAmount.value = 0
        _favouritesAssetListAmount.value = 0
        _allAssetList.value = null
        _allAssetListAmount.value = 0
    }
}