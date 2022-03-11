package com.example.itoken.presentation.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.itoken.databinding.ViewTradeCardviewBinding
import com.example.itoken.data.entity.Trade

class TradeAdapter(private val tradeList: List<Trade>?) : RecyclerView.Adapter<TradeAdapter.TradeViewHolder>() {

    var onClick: ((Trade?) -> (Unit))? = null

    inner class TradeViewHolder(
        private val binding: ViewTradeCardviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Trade?) = with(binding) {
//            tvCreatorName.text = item?.trader?.user?.username
//            ivCreator.load(Uri.parse(item?.trader?.profile_img_url))
//            tvTokenName.text = item?.asset?.name
//            tvPrice.text = item?.maxPrice?.toString()
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

    override fun onBindViewHolder(holder: TradeAdapter.TradeViewHolder, position: Int) =
        holder.bind(tradeList?.get(position))

    override fun getItemCount(): Int = tradeList?.size ?: 1

}