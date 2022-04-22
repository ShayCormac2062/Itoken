package com.example.itoken.features.trades.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itoken.features.trades.domain.model.Lot
import com.example.itoken.features.trades.domain.model.TradeModel
import com.example.itoken.features.trades.domain.usecase.CreateTradeUseCase
import com.example.itoken.features.trades.domain.usecase.GetActiveTradesUseCase
import com.example.itoken.features.trades.domain.usecase.GetAllTradesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class TradeViewModel @Inject constructor(
    private val createTradeUseCase: CreateTradeUseCase,
    private val getActiveTradesUseCase: GetActiveTradesUseCase,
    private val getAllTradesUseCase: GetAllTradesUseCase,
) : ViewModel() {

    private var _tradeList: MutableLiveData<List<TradeModel>?> = MutableLiveData()
    val tradeList: LiveData<List<TradeModel>?> = _tradeList

    fun getAllTrades() {
        viewModelScope.launch {
            try {
                _tradeList.value = getAllTradesUseCase()
            } catch (ex: Exception) {
                Log.e("DAMN", ex.message.toString())
                _tradeList.value = null
            }
        }
    }

    fun getActiveTrades() {
        viewModelScope.launch {
            try {
                _tradeList.value = getActiveTradesUseCase()
            } catch (ex: Exception) {
                ex.printStackTrace()
                _tradeList.value = null
            }
        }
    }

    fun createTrade(lot: Lot) {
        viewModelScope.launch {
            createTradeUseCase(lot)
        }
    }

    fun update() {
        _tradeList.value = null
    }
}