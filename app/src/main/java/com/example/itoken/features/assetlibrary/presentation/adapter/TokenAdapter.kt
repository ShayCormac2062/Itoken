package com.example.itoken.features.assetlibrary.presentation.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.itoken.R
import com.example.itoken.databinding.ViewTokenCardviewBinding
import com.example.itoken.features.assetlibrary.presentation.model.AssetBrief
import com.example.itoken.features.user.presentation.model.ItemAssetBrief

class TokenAdapter(val tokenList: List<AssetBrief>?, private val context: Context) : RecyclerView.Adapter<TokenAdapter.TokenCollectionViewHolder>() {

    var onClick: ((AssetBrief?, Int) -> (Unit))? = null
    var onLastCardClick: ((AssetBrief, Int) -> (Unit))? = null

    inner class TokenCollectionViewHolder(
        private val binding: ViewTokenCardviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AssetBrief?, isTheLast: Boolean) {
            with(binding) {
                if (!isTheLast) {
                    item?.let { onClick?.let { it1 -> binding.collectionCardview.init(it, it1, false) } }
                } else {
                    item?.let {
                        onLastCardClick?.let { it1 ->
                            binding.collectionCardview.init(
                                it,
                                it1,
                                true
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TokenCollectionViewHolder =
        TokenCollectionViewHolder(
            ViewTokenCardviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: TokenCollectionViewHolder, position: Int) =
        holder.bind(tokenList?.get(position), position == 9)

    override fun getItemCount(): Int {
        tokenList?.let {
            return if (it.size > 10) 10 else it.size
        }
        return 0
    }

}
