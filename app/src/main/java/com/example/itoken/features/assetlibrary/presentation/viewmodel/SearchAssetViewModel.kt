package com.example.itoken.features.assetlibrary.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itoken.features.assetlibrary.domain.model.InfoAsset
import com.example.itoken.features.assetlibrary.domain.usecase.search.GetAssetsByCategoryUseCase
import com.example.itoken.features.assetlibrary.domain.usecase.search.GetAssetsBySearchUseCase
import com.example.itoken.features.assetlibrary.domain.usecase.search.GetAssetsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchAssetViewModel @Inject constructor(
    private val getAssetsByCategoryUseCase: GetAssetsByCategoryUseCase,
    private val getAssetsBySearchUseCase: GetAssetsBySearchUseCase,
    private val getAssetsUseCase: GetAssetsUseCase,
) : ViewModel() {

    private var _allAssetList: MutableLiveData<List<InfoAsset>?> = MutableLiveData()
    val allAssetList: LiveData<List<InfoAsset>?> = _allAssetList

    private var _categoryAssetList: MutableLiveData<List<InfoAsset>?> = MutableLiveData()
    val categoryAssetList: LiveData<List<InfoAsset>?> = _categoryAssetList

    private var _searchAssetList: MutableLiveData<List<InfoAsset>?> = MutableLiveData()
    val searchAssetList: LiveData<List<InfoAsset>?> = _searchAssetList

    fun getAllAssets() {
        viewModelScope.launch {
            try {
                val assetList = arrayListOf<InfoAsset>()
                getAssetsUseCase().forEach(action = {
                    assetList.add(it)
                })
                _allAssetList.value = assetList
            } catch (ex: Exception) {
                _allAssetList.value = null
            }
        }
    }

    fun getSearchResponse(request: String) {
        viewModelScope.launch {
            try {
                val assetList = arrayListOf<InfoAsset>()
                getAssetsBySearchUseCase(request).forEach(action = {
                    assetList.add(it)
                })
                _searchAssetList.value = assetList
            } catch (ex: Exception) {
                _searchAssetList.value = null
            }
        }
    }

    fun getCategoryResponse(category: String) {
        viewModelScope.launch {
            try {
                val assetList = arrayListOf<InfoAsset>()
                getAssetsByCategoryUseCase(category).forEach(action = {
                    assetList.add(it)
                })
                _categoryAssetList.value = assetList
            } catch (ex: Exception) {
                _categoryAssetList.value = null
            }
        }
    }

    fun close() {
        _allAssetList.value = null
    }
}