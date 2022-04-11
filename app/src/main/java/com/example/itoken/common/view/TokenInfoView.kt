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

class TokenInfoView<M: BaseAsset>(
    context: Context,
    attrs: AttributeSet? = null
) : NestedScrollView(context) {

    private val binding by lazy { FragmentTokenInfoBinding.bind(this) }

    fun init(asset: M, likes: Int) {
        binding.run {
            imageView.load(Uri.parse(asset.imagePreviewUrl))
            ivToken.load(Uri.parse(asset.imageUrl))
            tvCreatorName.text = asset.creatorName ?: "Автор неизвестен"
            tvTokenName.text = asset.tokenName ?: "Название не указано"
            tvPrice.text = "Цена: ${asset.price} ICrystal"
            tvFavourite.text = likes.toString()
            tvDescription.text = asset.description
            try {
                tvContractAddressValue.text = StringBuilder()
                    .append(asset.address?.substring(14, 28))
                    .append("...")
            } catch (e: Exception) {
                tvContractAddressValue.text = asset.address
            }
            tvTokenIdValue.text = asset.tokenId?.let {
                if (it.length > 10) it.substring(0, 9)
                else it
            }.toString()
            tvTokenStandardsValue.text = (100..365).random().toString()
            tvBlockchainValue.text = asset.ownerName?.let {
                if (it.length > 30) "Имя не указано"
                else it
            } ?: "Имя не указано"
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