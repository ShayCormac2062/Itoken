package com.example.itoken.data.response

data class Trade(val id: Int,
                 val asset: Asset,
                 val trader: Creator,
                 val buyers: Map<Owner, Int>?,
                 val minPrice: Int?)
