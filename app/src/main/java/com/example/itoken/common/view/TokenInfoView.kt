package com.example.itoken.common.view

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.widget.NestedScrollView
import coil.load
import com.example.itoken.R
import com.example.itoken.common.entity.BaseAsset
import com.example.itoken.databinding.FragmentTokenInfoBinding

class TokenInfoView<M : BaseAsset>(
    context: Context,
    attrs: AttributeSet? = null
) : NestedScrollView(context) {

    private val binding by lazy { FragmentTokenInfoBinding.bind(this) }

    fun init(asset: M, likes: Int) {
        binding.run {
            imageView.load(Uri.parse(asset.imagePreviewUrl))
            ivToken.load(Uri.parse(asset.imageUrl))
            tvCreatorName.text = asset.creatorName
            tvTokenName.text = asset.tokenName
            tvPrice.text = "Цена: ${asset.price} ICrystal"
            tvFavourite.text = likes.toString()
            tvDescription.text = asset.description
            tvContractAddressValue.text = asset.address
            tvTokenIdValue.text = asset.tokenId
            tvTokenStandardsValue.text = (100..365).random().toString()
            tvBlockchainValue.text = asset.ownerName
            tvMetadataValue.text = "По умолчанию"
            cardviewShowDetails.setOnClickListener {
                onDetailClick(cardviewDetails.visibility == View.VISIBLE)
            }
        }
    }

    private fun onDetailClick(isEnabled: Boolean) {
        binding.cardviewDetails.run {
            if (!isEnabled) visibility = View.VISIBLE
            startAnimation(
                AnimationUtils
                    .loadAnimation(
                        context,
                        if (!isEnabled) R.anim.swipe_down else R.anim.swipe_up
                    )
            )
            if (isEnabled) visibility = View.GONE
            binding.ivCollectedCard.load(if (isEnabled) R.drawable.arrow_down else R.drawable.arrow_up)
        }
    }
}