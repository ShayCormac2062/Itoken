package com.example.itoken.features.assetlibrary.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itoken.App
import com.example.itoken.R
import com.example.itoken.common.fragment.TokenInfoFragment
import com.example.itoken.features.assetlibrary.data.response.Asset
import com.example.itoken.databinding.FragmentAllTokensBinding
import com.example.itoken.features.assetlibrary.presentation.adapter.CollectionAdapter
import com.example.itoken.features.assetlibrary.presentation.adapter.GenreCollectionAdapter
import com.example.itoken.features.assetlibrary.presentation.adapter.TokenAdapter
import com.example.itoken.features.assetlibrary.presentation.model.AssetBrief
import com.example.itoken.features.assetlibrary.presentation.viewmodel.MainViewModel
import com.example.itoken.features.assetlibrary.domain.model.InfoAsset
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllTokensFragment : Fragment() {

    private var binding: FragmentAllTokensBinding? = null
    private var collections: List<Asset>? = null //TODO(сделай api для коллекций, даун!!!)

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: MainViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllTokensBinding.inflate(layoutInflater)
        initObservers()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.run {
            rvGenres.apply {
                layoutManager = LinearLayoutManager(context).apply {
                    orientation = RecyclerView.HORIZONTAL
                }
                adapter = GenreCollectionAdapter().apply {
                    setItemViewCacheSize(10)
                    onClick = {
                    }
                }
            }
            try {
                (rvCheapTokens.adapter as TokenAdapter).tokenList
            } catch (ex: Exception) {
                setupScreen()
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

    private fun initObservers() {
        binding?.run {
            with(viewModel) {
                assetListCheap.observe(viewLifecycleOwner) {
                    it?.fold(onSuccess = { t ->
                        if (t.isNotEmpty()) {
                            initTokensRecyclerView(rvCheapTokens, slCheapTokens, t)
                        } else {
                            findNavController().navigate(R.id.noConnectionFragment).apply {
                                NoConnectionFragment.previousFragment = R.id.allTokensFragment
                            }
                        }
                    }, onFailure = { error ->
                        Log.e("FUCK", error.message.toString())
                    })
                }
                assetList.observe(viewLifecycleOwner) {
                    it?.fold(onSuccess = { t ->
                        initTokensRecyclerView(rvTokens, slTokens, t)
                    }, onFailure = { error ->
                        Log.e("FUCK", error.message.toString())
                    })
                }
            }
        }
    }

    private fun setupScreen() {
        lifecycleScope.launch {
            binding?.run {
                slGenres.startShimmer()
                slTokens.startShimmer()
                slCheapTokens.startShimmer()
                viewModel.getAssets()
                viewModel.getAssetsBrief()
                initRecycler(slGenres, rvGenres)
            }
        }
    }

    private fun initTokensRecyclerView(
        rv: RecyclerView,
        shimmer: ShimmerFrameLayout,
        list: List<InfoAsset>?
    ) {
        rv.apply {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = RecyclerView.HORIZONTAL
            }
            val briefList = arrayListOf<AssetBrief>()
            list?.forEach(action = {
                briefList.add(it.toAssetBrief())
            })
            adapter = TokenAdapter(briefList, context).apply {
                onClick = { asset, likes ->
                    swapTokenInfoBottomSheet(
                        list?.first(
                            predicate = {
                                it.imageUrl == asset?.imageUrl
                            }
                        ) as InfoAsset, likes)
                }
                setItemViewCacheSize(20)
                onLastCardClick = { _, _ ->
                    Toast.makeText(
                        context,
                        "Переброс на страницу с токенами",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            initRecycler(shimmer, rv)
        }
    }

    private fun initRecycler(shimmer: ShimmerFrameLayout, rv: RecyclerView) {
        with(shimmer) {
            stopShimmer()
            visibility = View.GONE
        }
        rv.visibility = View.VISIBLE
    }

    private fun swapTokenInfoBottomSheet(asset: InfoAsset, likes: Int) {
        parentFragmentManager.beginTransaction()
            .add(TokenInfoFragment().apply {
                TokenInfoFragment.asset = asset
                TokenInfoFragment.likes = likes
            }, "SHIT")
            .commit()
    }

}
