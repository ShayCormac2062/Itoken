package com.example.itoken.features.trades.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itoken.features.trades.domain.model.Lot
import com.example.itoken.features.trades.domain.model.TradeModel
import com.example.itoken.features.trades.domain.usecase.CreateTradeUseCase
import com.example.itoken.features.trades.domain.usecase.GetAllTradesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class TradeViewModel @Inject constructor(
    private val createTradeUseCase: CreateTradeUseCase,
    private val getAllTradesUseCase: GetAllTradesUseCase,
) : ViewModel() {

    private var _allTradeList: MutableLiveData<List<TradeModel>?> = MutableLiveData()
    val allTradeList: LiveData<List<TradeModel>?> = _allTradeList

    fun getAllTrades() {
        viewModelScope.launch {
            try {
                _allTradeList.value = getAllTradesUseCase()
            } catch (ex: Exception) {
                _allTradeList.value = arrayListOf()
            }
        }
    }

    fun createTrade(lot: Lot) {
        viewModelScope.launch {
            createTradeUseCase(lot)
        }
    }

}