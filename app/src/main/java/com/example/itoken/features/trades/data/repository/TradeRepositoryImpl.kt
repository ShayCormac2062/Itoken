package com.example.itoken.features.trades.data.repository

import com.example.itoken.features.trades.data.entity.TradingAsset
import com.example.itoken.features.trades.data.entity.Trade
import com.example.itoken.features.trades.domain.model.Auctioneer
import com.example.itoken.features.trades.domain.model.TradeModel
import com.example.itoken.features.trades.domain.repository.TradeRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await
import java.util.ArrayList
import javax.inject.Inject

class TradeRepositoryImpl @Inject constructor(
    private val ref: DatabaseReference
) : TradeRepository {

    override suspend fun getAllTrades(): List<TradeModel> {
        val result = arrayListOf<TradeModel>()
        val snapshot = ref.get().await()
        for (dto in snapshot.child("trades").children) {
            if (dto.child("active").value == false) {
                result.add(retrieveAsset(dto))
            }
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
            TradingAsset(
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
            getListOfMembers(dto.child("candidates"))
        ).toTradeModel()

    private fun getListOfMembers(dto: DataSnapshot): ArrayList<Auctioneer> {
        val result = arrayListOf<Auctioneer>()
        for (member in dto.children) {
            result.add(
                Auctioneer(
                member.child("stringId").value as String?,
                member.child("name").value as String?,
                member.child("price").value as Long?,
                member.child("imageUrl").value as String?,
            ))
        }
        return result
    }

}