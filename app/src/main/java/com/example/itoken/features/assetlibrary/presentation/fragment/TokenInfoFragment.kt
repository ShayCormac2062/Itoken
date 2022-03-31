package com.example.itoken.features.assetlibrary.presentation.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import android.view.animation.AnimationUtils
import com.example.itoken.R
import com.example.itoken.databinding.FragmentTokenInfoBinding
import com.example.itoken.features.assetlibrary.domain.model.InfoAsset
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TokenInfoFragment(private val asset: InfoAsset?, private val likes: Int?) : BottomSheetDialogFragment() {

    private var binding: FragmentTokenInfoBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTokenInfoBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run {
            if (asset?.imagePreviewUrl != null) imageView.load(Uri.parse(asset.imagePreviewUrl))
            else imageView.load(R.drawable.gradient_login)
            ivToken.load(Uri.parse(asset?.imageUrl))
            tvCreatorName.text = asset?.creatorName ?: "Автор неизвестен"
            tvTokenName.text = asset?.tokenName?.let {
                (if (it.length > 32) {
                    StringBuilder().append(it.substring(0, 29))
                        .append("...")
                } else it)
            } ?: "Название не указано"
            tvPrice.text = "Цена: ${asset?.price} ICrystal"
            tvFavourite.text = likes.toString()
            tvDescription.text = asset?.description ?: asset?.description
            tvContractAddressValue.text = StringBuilder()
                .append(asset?.address?.substring(0, 14))
                .append("...")
            tvTokenIdValue.text = asset?.tokenId?.let {
                if (it.toString().length > 10) it.toString().substring(0, 9)
                else it
            }.toString()
            tvTokenStandardsValue.text = (100..365).random().toString()
            tvBlockchainValue.text = asset?.ownerName?.let {
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
        binding?.cardviewDetails?.run {
            if (!isEnabled) visibility = View.VISIBLE
            startAnimation(AnimationUtils
                .loadAnimation(
                context,
                if (!isEnabled) R.anim.swipe_down else R.anim.swipe_up)
            )
            if (isEnabled) visibility = View.GONE
            binding?.ivCollectedCard?.load(if (isEnabled) R.drawable.arrow_down else R.drawable.arrow_up)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
