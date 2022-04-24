package com.example.itoken.features.trades.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itoken.features.trades.domain.model.Auctioneer
import com.example.itoken.features.trades.domain.model.Lot
import com.example.itoken.features.trades.domain.usecase.ChangeMemberListUseCase
import com.example.itoken.features.trades.domain.usecase.CloseTradeUseCase
import com.example.itoken.features.trades.domain.usecase.SendTokenToUserUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class TransactionViewModel @Inject constructor(
    private val sendTokenToUserUseCase: SendTokenToUserUseCase,
    private val closeTradeUseCase: CloseTradeUseCase,
    private val changeMemberListUseCase: ChangeMemberListUseCase,
) : ViewModel() {

    fun sendTokenToUser(userId: String?, trade: Lot) {
        viewModelScope.launch {
            sendTokenToUserUseCase(userId, trade)
        }
    }

    fun closeTrade(tradeId: String?) {
        viewModelScope.launch {
            closeTradeUseCase(tradeId)
        }
    }

    fun changeMembersList(tradeId: String?, members: List<Auctioneer>?) {
        viewModelScope.launch {
            changeMemberListUseCase(tradeId, members)
        }
    }

}