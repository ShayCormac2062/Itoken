package com.example.itoken.features.assetlibrary.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itoken.databinding.ViewTokenCardviewBinding
import com.example.itoken.features.assetlibrary.presentation.model.AssetBrief

class TokenAdapter(private val tokenList: List<AssetBrief>?) :
    RecyclerView.Adapter<TokenAdapter.TokenCollectionViewHolder>() {

    var onClick: ((AssetBrief?, Long) -> (Unit))? = null
    var onLastCardClick: ((AssetBrief, Long) -> (Unit))? = null

    inner class TokenCollectionViewHolder(
        private val binding: ViewTokenCardviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AssetBrief?, isTheLast: Boolean) {
            if (!isTheLast) {
                item?.let {
                    onClick?.let { it1 ->
                        binding.collectionCardview.init(it, it1, false)
                    }
                }
            } else {
                item?.let {
                    onLastCardClick?.let { it1 ->
                        binding.collectionCardview.init(it, it1, true)
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
        holder.bind(tokenList?.get(position), position == (tokenList?.size?.minus(1)))

    override fun getItemCount(): Int {
        tokenList?.let {
            return it.size
        }
        return 0
    }

}
