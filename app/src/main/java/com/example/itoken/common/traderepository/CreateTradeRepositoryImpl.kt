package com.example.itoken.common.traderepository

import android.net.Uri
import com.example.itoken.features.trades.data.entity.Trade
import com.example.itoken.features.trades.domain.model.Lot
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CreateTradeRepositoryImpl @Inject constructor(
    private val ref: DatabaseReference,
    private val storage: FirebaseStorage,
) : CreateTradeRepository {

    override suspend fun createTrade(trade: Lot) {
        val reference = storage.getReference("${trade.address}.jpg")
        val url = getDownloadUrl(reference, trade)
        with(trade) {
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

    private suspend fun getDownloadUrl(reference: StorageReference, trade: Lot): Uri {
        val uploadTask = reference.putFile(Uri.parse(trade.imageUrl))
        return suspendCoroutine { continuation ->
            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                reference.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUrl = task.result
                    continuation.resume(downloadUrl)
                } else {
                    task.exception?.let {
                        continuation.resumeWithException(it)
                    }
                }
            }
        }
    }
}