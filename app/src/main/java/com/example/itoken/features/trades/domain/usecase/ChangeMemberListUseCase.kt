package com.example.itoken.features.trades.domain.usecase

import com.example.itoken.features.trades.domain.model.Auctioneer
import com.example.itoken.features.trades.domain.repository.TransactionRepository
import javax.inject.Inject

class ChangeMemberListUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(tradeId: String?, member: Auctioneer?) =
        repository.changeMembersList(tradeId, member)

}