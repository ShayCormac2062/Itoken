package com.example.itoken.features.trades.data

import com.example.itoken.features.trades.data.entity.TradableAsset
import com.example.itoken.features.trades.data.entity.Trade
import com.example.itoken.features.trades.domain.model.TradeModel
import com.example.itoken.features.trades.domain.repository.TradeRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TradeRepositoryImpl @Inject constructor(
    private val ref: DatabaseReference
) : TradeRepository {

    override suspend fun getAllTrades(): List<TradeModel> {
        val result = arrayListOf<TradeModel>()
        val snapshot = ref.get().await()
        for (dto in snapshot.child("trades").children) {
            result.add(retrieveAsset(dto))
        }
        return result
    }

    override suspend fun getActiveTrades(): List<TradeModel> {
        val result = arrayListOf<TradeModel>()
        val snapshot = ref.get().await()
        for (dto in snapshot.child("trades").children) {
            if (dto.child("active").value == true) {
                result.add(retrieveAsset(dto))
            }
        }
        return result
    }

    private fun retrieveAsset(dto: DataSnapshot?): TradeModel =
        Trade(
            dto?.child("id")?.value as Long,
            TradableAsset(
                dto.child("token").child("tokenId").value as String?,
                dto.child("token").child("imagePreviewUrl").value as String?,
                dto.child("token").child("imageUrl").value as String?,
                dto.child("token").child("creatorName").value as String?,
                dto.child("token").child("ownerName").value as String?,
                dto.child("token").child("tokenName").value as String?,
                dto.child("token").child("price").value as Long?,
                dto.child("token").child("likes").value as Long?,
                dto.child("token").child("description").value as String?,
                dto.child("token").child("address").value as String?,
            ),
            dto.child("author").value as String?,
            dto.child("price").value as Long?,
            dto.child("active").value as Boolean,
            dto.child("candidates").value as String?
        ).toTradeModel()

}