package com.example.itoken.presentation.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.example.itoken.R
import com.example.itoken.databinding.FragmentTokenInfoBinding
import com.example.itoken.domain.model.InfoAsset
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
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
