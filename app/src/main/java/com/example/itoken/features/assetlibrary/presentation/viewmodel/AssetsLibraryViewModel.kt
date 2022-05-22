package com.example.itoken.features.assetlibrary.presentation.viewmodel

import androidx.lifecycle.*
import com.example.itoken.features.assetlibrary.domain.usecase.library.GetAssetsBriefUseCase
import com.example.itoken.features.assetlibrary.domain.model.InfoAsset
import com.example.itoken.features.assetlibrary.domain.usecase.library.GetAssetsCheapUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class AssetsLibraryViewModel @Inject constructor(
    private val getAssetsBriefUseCase: GetAssetsBriefUseCase,
    private val getAssetsCheapUseCase: GetAssetsCheapUseCase
    ): ViewModel() {

    private var _assetList: MutableLiveData<List<InfoAsset>?> = MutableLiveData()
    val assetList: LiveData<List<InfoAsset>?> = _assetList

    private var _assetListCheap: MutableLiveData<List<InfoAsset>?> = MutableLiveData()
    val assetListCheap: LiveData<List<InfoAsset>?> = _assetListCheap

    fun getAssetsCheap() {
        viewModelScope.launch {
            try {
                val assetList = arrayListOf<InfoAsset>()
                getAssetsCheapUseCase().forEach(action = {
                    assetList.add(it)
                })
                _assetListCheap.value = assetList
            } catch (ex: Exception) {
                _assetListCheap.value = null
            }
        }
    }

    fun getTenAssets() {
        viewModelScope.launch {
            try {
                val assetList = arrayListOf<InfoAsset>()
                getAssetsBriefUseCase().forEach(action = {
                    assetList.add(it)
                })
                _assetList.value = assetList
            } catch (ex: Exception) {
                _assetList.value = null
            }
        }
    }

    fun close() {
        _assetList.value = null
        _assetListCheap.value = null
    }

}
