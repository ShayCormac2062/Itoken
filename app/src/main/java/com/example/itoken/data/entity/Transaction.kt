package com.example.itoken.data.entity

data class Transaction(
    val block_hash: String,
    val block_number: String,
    val from_account: FromAccount,
    val id: Int,
    val timestamp: String,
    val to_account: ToAccount,
    val transaction_hash: String,
    val transaction_index: String
)