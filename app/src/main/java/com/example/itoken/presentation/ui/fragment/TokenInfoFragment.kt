package com.example.itoken.presentation.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.example.itoken.R
import com.example.itoken.data.response.Asset
import com.example.itoken.databinding.FragmentTokenInfoBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TokenInfoFragment(private val asset: Asset?, private val likes: Int?) : BottomSheetDialogFragment() {

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
            if (asset?.image_preview_url != null) imageView.load(Uri.parse(asset.image_preview_url))
            else imageView.load(R.drawable.gradient_login)
            ivToken.load(Uri.parse(asset?.image_url))
            tvCreatorName.text = asset?.creator?.user?.username ?: "Автор неизвестен"
            tvTokenName.text = asset?.name ?: "Название не указано"
            tvPrice.text = "Цена: ${asset?.asset_contract?.seller_fee_basis_points ?: 0} ICrystal"
            tvFavourite.text = likes.toString()
            tvDescription.text = asset?.description ?: asset?.collection?.description
            tvContractAddressValue.text = StringBuilder()
                .append(asset?.asset_contract?.address?.substring(0, 14))
                .append("...")
            tvTokenIdValue.text = asset?.token_id?.let {
                if (it.length > 10) it.substring(0, 9)
                else it
            }
            tvTokenStandardsValue.text = "001"
            tvBlockchainValue.text = asset?.owner?.user?.username?.let {
                if (it.length > 30) "Имя не указано"
                else it
            } ?: "Имя не указано"
            tvMetadataValue.text = asset?.token_metadata?.let {
                if (it.toString().length > 20) "Не указано"
                else it.toString()
            } ?: "По умолчанию"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}