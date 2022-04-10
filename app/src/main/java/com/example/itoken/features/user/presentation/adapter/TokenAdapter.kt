package com.example.itoken.features.user.presentation.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.itoken.databinding.ViewTokenCardviewBinding
import com.example.itoken.features.user.presentation.model.ItemAssetBrief

class TokenAdapter(val tokenList: List<ItemAssetBrief>?, private val context: Context) :
    RecyclerView.Adapter<TokenAdapter.TokenCollectionViewHolder>() {

    var onClick: ((ItemAssetBrief?, Int?) -> (Unit))? = null

    inner class TokenCollectionViewHolder(
        private val binding: ViewTokenCardviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemAssetBrief?) {
            with(binding) {
                tvCreatorName.text = item?.creatorName?.let {
                    (if (it.length > 18) {
                        StringBuilder().append(it.substring(0, 15))
                            .append("...")
                    } else it)
                } ?: "Автор неизвестен"
                tvTokenName.text = item?.tokenName?.let {
                    (if (it.length > 32) {
                        StringBuilder().append(it.substring(0, 29))
                            .append("...")
                    } else it)
                } ?: "Название не указано"
                ivTokenImage.load(Uri.parse(item?.imageUrl))
                tvTokenPrice.text = item?.price.toString()
                tvLikesAmount.text = item?.likes?.toString()
                collectionCardview.setOnClickListener {
                    onClick?.invoke(item, (tvLikesAmount.text as String).toInt())
                }
            }
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