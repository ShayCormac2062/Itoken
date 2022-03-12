package com.example.itoken.presentation.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.itoken.R
import com.example.itoken.databinding.ViewTokenCardviewBinding
import com.example.itoken.data.entity.Asset

class TokenAdapter(private var tokenList: List<Asset>?, private val context: Context) : RecyclerView.Adapter<TokenAdapter.TokenCollectionViewHolder>() {

    var onClick: ((Asset?) -> (Unit))? = null
    var onLastCardClick: (() -> (Unit))? = null

    inner class TokenCollectionViewHolder(
        private val binding: ViewTokenCardviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Asset?, isTheLast: Boolean) {
            with(binding) {
                if (!isTheLast) {
                    tvCreatorName.text = item?.creator?.user?.username ?: "Автор неизвестен"
                    tvTokenName.text = item?.name.toString()
                    ivTokenImage.load(Uri.parse(item?.image_url))
                    tvTokenPrice.text = item?.transfer_fee?.toString() ?: 0.toString()
                    tvLikesAmount.text = item?.decimals?.toString()
                    collectionCardview.setOnClickListener {
                        onClick?.invoke(item)
                    }
                } else {
                    with(collectionCardview) {
                        ivCrystal.visibility = View.INVISIBLE
                        ivLike.visibility = View.INVISIBLE
                        collectionLayout.setBackgroundResource(R.drawable.gradient_login)
                        tvTokenName.setTextColor(resources.getColor(R.color.white))
                        tvTokenName.textSize = 16.0f
                        setOnClickListener {
                            onLastCardClick?.invoke()
                        }
                    }
                    tvTokenName.text = context.getString(R.string.see_all_tokens_notif)
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