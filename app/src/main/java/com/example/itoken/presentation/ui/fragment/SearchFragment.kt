package com.example.itoken.presentation.ui.fragment

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.itoken.R
import com.example.itoken.data.response.Asset
import com.example.itoken.databinding.FragmentSearchBinding
import com.example.itoken.databinding.FragmentTokenInfoBinding
import com.example.itoken.presentation.adapter.GenreCollectionAdapter
import com.example.itoken.presentation.adapter.TokenAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog

class SearchFragment : Fragment() {

    private var binding: FragmentSearchBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.run {
            rvGenres.apply {
                layoutManager = LinearLayoutManager(context).apply {
                    orientation = RecyclerView.HORIZONTAL
                }
                adapter = GenreCollectionAdapter()
            }
            rvPopularTokens.apply {
                layoutManager = GridLayoutManager(context, 2).apply {
                    orientation = RecyclerView.VERTICAL
                }
                adapter = TokenAdapter( null, context).apply {
                    onClick = { asset, likes ->
                        swapTokenInfoBottomSheet(asset, likes)
                    }
                }
            }
        }
    }

    private fun swapTokenInfoBottomSheet(asset: Asset?, likes: Int?) {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_token_info, null)
        val bindingForSheet = FragmentTokenInfoBinding.inflate(LayoutInflater.from(context))
        with(bindingForSheet) {
            if (asset?.image_preview_url != null) imageView.load(Uri.parse(asset.image_preview_url))
            else imageView.setImageResource(R.drawable.gradient_login)
            ivToken.load(Uri.parse(asset?.image_url))
            tvCreatorName.text = asset?.creator?.user?.username ?: "Автор неизвестен"
            tvTokenName.text = asset?.name
            tvPrice.text = "Цена: ${asset?.asset_contract?.opensea_seller_fee_basis_points} ICrystal"
            tvFavourite.text = likes.toString()
            tvDescription.text = asset?.description ?: asset?.collection?.description
            tvContractAddressValue.text = StringBuilder()
                .append(asset?.asset_contract?.address?.substring(0, 14))
                .append("...")
            tvTokenIdValue.text = asset?.token_id?.substring(0, 9)
            tvTokenStandardsValue.text = "001"
            tvBlockchainValue.text = asset?.owner?.user?.username
            tvMetadataValue.text = asset?.token_metadata?.toString() ?: "По умолчанию"
        }
        activity?.let { BottomSheetDialog(it) }?.apply {
            setContentView(view)
            show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}