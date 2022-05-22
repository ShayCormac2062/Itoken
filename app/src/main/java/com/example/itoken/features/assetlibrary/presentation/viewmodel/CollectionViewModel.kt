package com.example.itoken.features.assetlibrary.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itoken.features.assetlibrary.domain.model.InfoCollection
import com.example.itoken.features.assetlibrary.domain.usecase.library.GetCollectionsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CollectionViewModel @Inject constructor(
    private val getCollectionsUseCase: GetCollectionsUseCase,
) : ViewModel() {

    private var _collectionList: MutableLiveData<List<InfoCollection>?> = MutableLiveData()
    val collectionList: LiveData<List<InfoCollection>?> = _collectionList

    fun getCollections() {
        viewModelScope.launch {
            try {
                _collectionList.value = getCollectionsUseCase()
            } catch (ex: Exception) {
                _collectionList.value = null
            }
        }
    }

    fun close() {
        _collectionList.value = null
    }

}