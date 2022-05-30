package com.example.itoken.common.traderepository

import android.net.Uri
import com.example.itoken.features.trades.data.entity.Trade
import com.example.itoken.features.trades.domain.model.Lot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject

class CreateTradeRepositoryImpl @Inject constructor(
    private val ref: DatabaseReference,
    private val storage: FirebaseStorage,
) : CreateTradeRepository {

    override suspend fun createTrade(trade: Lot) {
        storage.getReference("${trade.address}.jpg").apply {
            putFile(Uri.parse(trade.imageUrl)).addOnCompleteListener {
                if (it.isSuccessful) {
                    with(trade) {
                        downloadUrl.addOnSuccessListener { url ->
                            imageUrl = url.toString()
                            imagePreviewUrl = url.toString()
                            ref.child("trades")
                                .child(trade.address.toString())
                                .setValue(
                                    Trade(
                                        (0..9999999).random().toLong(),
                                        trade.toTradingAsset(),
                                        trade.creatorName,
                                        trade.price,
                                        true,
                                        arrayListOf()
                                    )
                                )
                        }
                    }
                }
            }
        }
    }
}