package com.example.itoken.features.assetlibrary.presentation.viewmodel

import androidx.lifecycle.*
import com.example.itoken.features.assetlibrary.domain.usecase.GetAssetsBriefUseCase
import com.example.itoken.features.assetlibrary.domain.usecase.GetAssetsUseCase
import com.example.itoken.features.assetlibrary.domain.model.InfoAsset
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getAssetsBriefUseCase: GetAssetsBriefUseCase,
    private val getAssetsUseCase: GetAssetsUseCase
    ): ViewModel() {

    private var _assetList: MutableLiveData<List<InfoAsset>?> = MutableLiveData()
    val assetList: LiveData<List<InfoAsset>?> = _assetList

    // TODO(
    //  Сюда надо будет добавить LiveData с коллекциями. ХВАТИТ ЛЕНИТЬСЯ, ТЫ, ВОНЮЧАЯ ЗАДНИЦА!
    //  Начни писать api для коллекций
    //  )

    private var _assetListCheap: MutableLiveData<List<InfoAsset>?> = MutableLiveData()
    val assetListCheap: LiveData<List<InfoAsset>?> = _assetListCheap

    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

    fun getAssetsBrief() {
        viewModelScope.launch {
            try {
                val assetList = arrayListOf<InfoAsset>()
                getAssetsBriefUseCase().forEach(action = {
                    assetList.add(it)
                })
                _assetListCheap.value = assetList
            } catch (ex: Exception) {
                _assetListCheap.value = null
                _error.value = ex
            }
        }
    }

    fun getAssets() {
        viewModelScope.launch {
            try {
                val assetList = arrayListOf<InfoAsset>()
                getAssetsBriefUseCase().forEach(action = {
                    assetList.add(it)
                })
                _assetList.value = assetList
            } catch (ex: Exception) {
                _assetList.value = null
                _error.value = ex
            }
        }
    }

    fun close() {
        _assetList.value = null
        _assetListCheap.value = null
    }
}
