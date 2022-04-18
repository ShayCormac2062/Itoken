package com.example.itoken.features.assetlibrary.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itoken.features.assetlibrary.domain.usecase.GetAssetsBriefUseCase
import com.example.itoken.features.assetlibrary.domain.usecase.GetAssetsUseCase
import com.example.itoken.features.assetlibrary.domain.model.InfoAsset
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getAssetsBriefUseCase: GetAssetsBriefUseCase,
    private val getAssetsUseCase: GetAssetsUseCase
    ): ViewModel() {

    private var _assetList: MutableLiveData<Result<List<InfoAsset>>?> = MutableLiveData()
    val assetList: LiveData<Result<List<InfoAsset>>?> = _assetList

    // TODO(
    //  Сюда надо будет добавить LiveData с коллекциями. ХВАТИТ ЛЕНИТЬСЯ, ТЫ, ВОНЮЧАЯ ЗАДНИЦА!
    //  Начни писать api для коллекций
    //  )

    private var _assetListCheap: MutableLiveData<Result<List<InfoAsset>>?> = MutableLiveData()
    val assetListCheap: LiveData<Result<List<InfoAsset>>?> = _assetListCheap

    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

    suspend fun getAssetsBrief() {
        viewModelScope.launch {
            try {
                val assetList = arrayListOf<InfoAsset>()
                getAssetsBriefUseCase().forEach(action = {
                    assetList.add(it)
                })
                _assetListCheap.value = Result.success(assetList)
                _assetListCheap.postValue(null)
            } catch (ex: Exception) {
                _assetListCheap.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

    suspend fun getAssets() {
        viewModelScope.launch {
            try {
                val assetList = arrayListOf<InfoAsset>()
                getAssetsBriefUseCase().forEach(action = {
                    assetList.add(it)
                })
                _assetList.value = Result.success(assetList)
                _assetList.postValue(null)
            } catch (ex: Exception) {
                _assetList.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

}
