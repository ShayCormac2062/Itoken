package com.example.itoken.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itoken.data.response.Asset
import com.example.itoken.domain.usecase.GetAssetsBriefUseCase
import com.example.itoken.domain.usecase.GetAssetsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getAssetsBriefUseCase: GetAssetsBriefUseCase,
    private val getAssetsUseCase: GetAssetsUseCase
    ): ViewModel() {


    private var _assetList: MutableLiveData<Result<List<Asset>>?> = MutableLiveData()
    val assetList: LiveData<Result<List<Asset>>?> = _assetList

    // TODO(
    //  Сюда надо будет добавить LiveData с коллекциями. ХВАТИТ ЛЕНИТЬСЯ, ТЫ, ВОНЮЧАЯ ЗАДНИЦА!
    //  Начни писать api для коллекций
    //  )

    private var _assetListCheap: MutableLiveData<Result<List<Asset>>?> = MutableLiveData()
    val assetListCheap: LiveData<Result<List<Asset>>?> = _assetListCheap

    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

    suspend fun getAssetsBrief() {
        viewModelScope.launch {
            try {
                val assetList = getAssetsBriefUseCase()
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
                val assetList = getAssetsBriefUseCase() //TODO(потом переделай)
                _assetList.value = Result.success(assetList)
                _assetList.postValue(null)
            } catch (ex: Exception) {
                _assetList.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

}