package com.example.itoken.features.assetlibrary.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itoken.App
import com.example.itoken.R
import com.example.itoken.common.fragment.TokenInfoFragment
import com.example.itoken.databinding.FragmentAllTokensBinding
import com.example.itoken.features.assetlibrary.data.response.Asset
import com.example.itoken.features.assetlibrary.domain.model.InfoAsset
import com.example.itoken.features.assetlibrary.presentation.adapter.CollectionAdapter
import com.example.itoken.features.assetlibrary.presentation.adapter.GenreCollectionAdapter
import com.example.itoken.features.assetlibrary.presentation.adapter.TokenAdapter
import com.example.itoken.features.assetlibrary.presentation.model.AssetBrief
import com.example.itoken.features.assetlibrary.presentation.viewmodel.AssetsLibraryViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import javax.inject.Inject

class AllTokensFragment : Fragment() {

    private var binding: FragmentAllTokensBinding? = null
    private var collections: List<Asset>? = null //TODO(сделай api для коллекций, даун!!!)

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: AssetsLibraryViewModel by viewModels {
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
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.run {
            initObservers()
            rvGenres.apply {
                layoutManager = LinearLayoutManager(context).apply {
                    orientation = RecyclerView.HORIZONTAL
                }
                adapter = GenreCollectionAdapter().apply {
                    setItemViewCacheSize(10)
                    onClick = {
                        findNavController().navigate(R.id.searchFragment).apply {
                            SearchFragment.category = it
                        }
                    }
                }
            }
            setupScreen()
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
        with(viewModel) {
            assetListCheap.observe(viewLifecycleOwner) { t ->
                observing(t, 1)
            }
            assetList.observe(viewLifecycleOwner) { t ->
                observing(t, 2)
            }
        }
    }

    private fun observing(t: List<InfoAsset>?, isForWho: Int) {
        binding?.run {
            if (t?.isNotEmpty() == true) {
                if (isForWho == 1) {
                    initTokensRecyclerView(rvCheapTokens, slCheapTokens, t)
                } else initTokensRecyclerView(rvTokens, slTokens, t)
            } else if (t != null) {
                findNavController().navigate(R.id.noConnectionFragment).apply {
                    NoConnectionFragment.previousFragment = R.id.allTokensFragment
                }
            }
        }
    }

    private fun setupScreen() {
        binding?.run {
            slGenres.startShimmer()
            slTokens.startShimmer()
            slCheapTokens.startShimmer()
            with(viewModel) {
                getAssetsCheap()
                getTenAssets()
            }
            initRecycler(slGenres, rvGenres)
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
            adapter = TokenAdapter(briefList).apply {
                onClick = { asset, likes ->
                    swapTokenInfoBottomSheet(
                        list?.first(
                            predicate = {
                                it.imageUrl == asset?.imageUrl &&
                                        it.likes == likes
                            }
                        ) as InfoAsset, likes)
                }
                setItemViewCacheSize(20)
                onLastCardClick = { _, _ ->
                    findNavController().navigate(R.id.searchFragment)
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

    private fun swapTokenInfoBottomSheet(asset: InfoAsset, likes: Long) {
        val bundle = Bundle().apply {
            putSerializable("asset", asset)
            putLong("likes", likes)
        }
        parentFragmentManager.beginTransaction()
            .add(TokenInfoFragment().apply {
                arguments = bundle
            }, "SHIT")
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.close()
        binding = null
    }

}
