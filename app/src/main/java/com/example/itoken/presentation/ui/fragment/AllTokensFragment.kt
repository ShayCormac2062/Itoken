package com.example.itoken.presentation.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itoken.R
import com.example.itoken.data.entity.Asset
import com.example.itoken.databinding.FragmentAllTokensBinding
import com.example.itoken.data.AssetRepositoryImpl
import com.example.itoken.databinding.FragmentTokenInfoBinding
import com.example.itoken.presentation.adapter.CollectionAdapter
import com.example.itoken.presentation.adapter.GenreCollectionAdapter
import com.example.itoken.presentation.adapter.TokenAdapter
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch

class AllTokensFragment : Fragment() {

    private var binding: FragmentAllTokensBinding? = null
    private val repository = AssetRepositoryImpl()
    private var tokens: List<Asset>? = null
    private var cheapTokens: List<Asset>? = null
    private var collections: List<Asset>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllTokensBinding.inflate(layoutInflater)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.run {
            slTokens.startShimmer()
            slCheapTokens.startShimmer()

                rvGenres.apply {
                    layoutManager = LinearLayoutManager(context).apply {
                        orientation = RecyclerView.HORIZONTAL
                    }
                    adapter = GenreCollectionAdapter().apply {
                        onClick = {
                        }
                    }
                }
            lifecycleScope.launch {
                tokens = repository.getAssetsBrief()
                cheapTokens = repository.getAssetsBrief()
                initTokensRecyclerView(rvTokens, slTokens, tokens)
                initTokensRecyclerView(rvCheapTokens, slCheapTokens, cheapTokens)
            }
            rvCollections.apply {
                layoutManager = LinearLayoutManager(context).apply {
                    orientation = RecyclerView.HORIZONTAL
                }
                adapter = context?.let {
                    CollectionAdapter(it, null).apply {
                        onClick = {
                            //TODO
                        }
                        onLastCardClick = {
                            Toast.makeText(
                                context,
                                "Переброс на страницу с коллекциями",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun initTokensRecyclerView(
        rv: RecyclerView,
        shimmer: ShimmerFrameLayout,
        list: List<Asset>?
    ) {
        rv.apply {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = RecyclerView.HORIZONTAL
            }
            adapter = TokenAdapter(list, context).apply {
                setItemViewCacheSize(20)
                onClick = { asset, likes ->
                    swapTokenInfoBottomSheet(asset, likes)
                }
                onLastCardClick = {
                    Toast.makeText(
                        context,
                        "Переброс на страницу с токенами",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            with(shimmer) {
                stopShimmer()
                visibility = View.GONE
            }
            visibility = View.VISIBLE
        }
    }

    private fun swapTokenInfoBottomSheet(asset: Asset?, likes: Int?) {
        val bindingForSheet = FragmentTokenInfoBinding.inflate(LayoutInflater.from(context))
        with(bindingForSheet) {
            if (asset?.image_preview_url != null) imageView.load(Uri.parse(asset.image_preview_url))
            else imageView.setImageResource(R.drawable.gradient_login)
            ivToken.load(Uri.parse(asset?.image_url))
            tvCreatorName.text = asset?.creator?.user?.username ?: "Автор неизвестен"
            tvTokenName.text = asset?.name
            tvPrice.text = "Цена: ${asset?.asset_contract?.seller_fee_basis_points} ICrystal"
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
            setContentView(bindingForSheet.root)
            show()
        }
    }
}