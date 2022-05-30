package com.example.itoken.features.trades.data.repository

import com.example.itoken.common.db.dao.GetUserDao
import com.example.itoken.features.trades.domain.model.Auctioneer
import com.example.itoken.features.trades.domain.model.Lot
import com.example.itoken.features.trades.domain.repository.TransactionRepository
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val firebase: DatabaseReference,
    private val getUserDatabase: GetUserDao
): TransactionRepository {

    override suspend fun sendTokenToUser(userId: String?, trade: Lot) {
        firebase.child("users")
            .child(userId.toString())
            .child("bought_assets")
            .push()
            .setValue(trade.apply {
                ownerName = "Вы"
            }).await()
    }

    override suspend fun transferMoneyToBarker(userId: String?, mark: Double?) {
        val newBalance = getUserDatabase.getUser()?.balance?.plus(mark ?: 0.0)
        getUserDatabase.changeBalance(newBalance)
        firebase.child("users")
            .child(userId.toString())
            .child("balance")
            .setValue(newBalance)
    }

    override suspend fun closeTrade(tradeId: String?) {
        firebase.child("trades")
            .child(tradeId.toString())
            .child("active")
            .setValue(false)
    }

    override suspend fun changeMembersList(tradeId: String?, member: Auctioneer?) {
        firebase.child("trades")
            .child(tradeId.toString())
            .child("candidates")
            .child(member?.stringId.toString())
            .setValue(member)
    }
}