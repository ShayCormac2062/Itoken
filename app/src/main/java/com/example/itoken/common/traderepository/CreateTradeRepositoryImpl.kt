package com.example.itoken.common.traderepository

import android.net.Uri
import com.example.itoken.features.trades.data.entity.Trade
import com.example.itoken.features.trades.domain.model.Lot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CreateTradeRepositoryImpl @Inject constructor(
    private val ref: DatabaseReference,
    private val storage: FirebaseStorage,
) : CreateTradeRepository {

    override suspend fun createTrade(trade: Lot) {
        val downloadUri = getDownloadUri(
            storage.getReference("${trade.address}.jpg"),
            Uri.parse(trade.imageUrl)
        ).first()
        with(trade) {
            imageUrl = downloadUri
            imagePreviewUrl = downloadUri
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

    private fun getDownloadUri(reference: StorageReference, parse: Uri) = flow {
        reference.putFile(parse).await()
        val resultTask = reference.downloadUrl.await()
        emit(resultTask.toString())
    }.catch {
        emit(parse.toString())
    }.flowOn(Dispatchers.IO)

}