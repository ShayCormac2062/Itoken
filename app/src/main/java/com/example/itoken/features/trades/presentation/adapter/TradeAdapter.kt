package com.example.itoken.features.trades.presentation.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.itoken.databinding.ViewTradeCardviewBinding
import com.example.itoken.features.trades.domain.model.TradeModel

class TradeAdapter(private val tradeList: List<TradeModel>?) : RecyclerView.Adapter<TradeAdapter.TradeViewHolder>() {

    var onClick: ((TradeModel?) -> (Unit))? = null

    inner class TradeViewHolder(
        private val binding: ViewTradeCardviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TradeModel?) = with(binding) {
            tvCreatorName.text = item?.author
            ivCreator.load(Uri.parse(item?.token?.imageUrl.toString()))
            tvTokenName.text = item?.token?.tokenName
            tvPrice.text = item?.price?.toString()
            tvStatus.text = "Статус: ${if (item?.isActive == true) "Активно" else "Завершено"}"
            tradeCardview.setOnClickListener {
                onClick?.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TradeViewHolder =
        TradeViewHolder(
            ViewTradeCardviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: TradeViewHolder, position: Int) =
        holder.bind(tradeList?.get(position))

    override fun getItemCount(): Int = tradeList?.size ?: 0

}
