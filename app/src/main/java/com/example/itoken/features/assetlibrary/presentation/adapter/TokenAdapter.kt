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

    var onClick: ((AssetBrief?, Int?) -> (Unit))? = null
    var onLastCardClick: (() -> (Unit))? = null

    inner class TokenCollectionViewHolder(
        private val binding: ViewTokenCardviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AssetBrief?, isTheLast: Boolean) {
            with(binding) {
                if (!isTheLast) {
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
                } else {
                    with(collectionCardview) {
                        ivCrystal.visibility = View.INVISIBLE
                        ivLike.visibility = View.INVISIBLE
                        collectionLayout.setBackgroundResource(R.drawable.gradient_login)
                        tvTokenName.setTextColor(resources.getColor(R.color.white))
                        tvTokenName.textSize = 17.0f
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
