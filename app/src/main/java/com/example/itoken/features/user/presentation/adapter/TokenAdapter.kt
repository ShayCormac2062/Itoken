package com.example.itoken.features.user.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itoken.databinding.ViewTokenCardviewBinding
import com.example.itoken.features.user.presentation.model.ItemAssetBrief

class TokenAdapter(private val tokenList: List<ItemAssetBrief>?) :
    RecyclerView.Adapter<TokenAdapter.TokenCollectionViewHolder>() {

    var onClick: ((ItemAssetBrief?, Int?) -> (Unit))? = null

    inner class TokenCollectionViewHolder(
        private val binding: ViewTokenCardviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemAssetBrief?) {
            item?.let { onClick?.let { it1 -> binding.collectionCardview.init(it, it1, false) } }
        }
    }

    override fun getItemCount(): Int {
        return tokenList?.size ?: 0
    }

    override fun onBindViewHolder(holder: TokenAdapter.TokenCollectionViewHolder, position: Int) {
        return holder.bind(tokenList?.get(position))
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TokenAdapter.TokenCollectionViewHolder {
        return TokenCollectionViewHolder(
            ViewTokenCardviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

}