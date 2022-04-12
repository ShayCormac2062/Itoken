package com.example.itoken.common.view

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import coil.load
import com.example.itoken.R
import com.example.itoken.common.entity.BaseAssetBrief
import com.example.itoken.databinding.ViewTokenCardviewBinding
import com.google.android.material.card.MaterialCardView

class TokenView<M : BaseAssetBrief>(
    context: Context,
    attrs: AttributeSet? = null
) : MaterialCardView(context) {

    private val binding by lazy { ViewTokenCardviewBinding.bind(this) }

    fun init(asset: M, onClick: ((M?, Int?) -> (Unit)), isTheLast: Boolean) {
        binding.run {
            if (!isTheLast) {
                tvCreatorName.text = asset.creatorName
                tvTokenName.text = asset.tokenName
                ivTokenImage.load(Uri.parse(asset.imageUrl))
                tvTokenPrice.text = asset.price.toString()
                tvLikesAmount.text = asset.likes?.toString()
                collectionCardview.setOnClickListener {
                    onClick.invoke(asset, (tvLikesAmount.text as String).toInt())
                }
            } else {
                ivCrystal.visibility = View.INVISIBLE
                ivLike.visibility = View.INVISIBLE
                collectionLayout.setBackgroundResource(R.drawable.gradient_login)
                tvTokenName.setTextColor(resources.getColor(R.color.white))
                tvTokenName.textSize = 17.0f
                setOnClickListener {
                    onClick.invoke(asset, 0)
                }
                tvTokenName.text = context.getString(R.string.see_all_tokens_notif)
            }
        }
    }
}