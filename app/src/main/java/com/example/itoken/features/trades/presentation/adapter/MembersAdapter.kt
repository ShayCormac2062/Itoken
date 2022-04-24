package com.example.itoken.features.trades.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.itoken.databinding.ViewMemberCardviewBinding
import com.example.itoken.features.trades.domain.model.Auctioneer

class MembersAdapter(private val members: List<Auctioneer>?, private val isUserOwner: Boolean?) :
    RecyclerView.Adapter<MembersAdapter.MemberViewHolder>() {

    var onClick: ((Auctioneer?) -> Unit)? = null

    inner class MemberViewHolder(
        private val binding: ViewMemberCardviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: Auctioneer?) {
            with(binding) {
                ivCreator.load(user?.imageUrl)
                tvMemberName.text = user?.name
                tvPriceAmount.text = user?.price.toString()
                if (isUserOwner == true) sendToken.apply {
                    visibility = View.VISIBLE
                    setOnClickListener {
                        onClick?.invoke(user)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder =
        MemberViewHolder(
            ViewMemberCardviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) =
        holder.bind(members?.get(position))

    override fun getItemCount(): Int = members?.size ?: 0

}