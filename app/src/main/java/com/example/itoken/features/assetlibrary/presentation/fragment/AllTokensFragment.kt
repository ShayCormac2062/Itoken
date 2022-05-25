package com.example.itoken.features.assetlibrary.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itoken.App
import com.example.itoken.R
import com.example.itoken.common.fragment.TokenInfoFragment
import com.example.itoken.common.viewmodel.CurrentUserViewModel
import com.example.itoken.databinding.FragmentAllTokensBinding
import com.example.itoken.features.addtoken.presentation.fragment.AddTokenFragment
import com.example.itoken.features.assetlibrary.domain.model.InfoAsset
import com.example.itoken.features.assetlibrary.domain.model.InfoCollection
import com.example.itoken.features.assetlibrary.presentation.adapter.CollectionAdapter
import com.example.itoken.features.assetlibrary.presentation.adapter.GenreCollectionAdapter
import com.example.itoken.features.assetlibrary.presentation.adapter.TokenAdapter
import com.example.itoken.features.assetlibrary.presentation.model.AssetBrief
import com.example.itoken.features.assetlibrary.presentation.model.CollectionBrief
import com.example.itoken.features.assetlibrary.presentation.viewmodel.AssetsLibraryViewModel
import com.example.itoken.features.assetlibrary.presentation.viewmodel.CollectionViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import javax.inject.Inject

class AllTokensFragment : Fragment() {

    private var binding: FragmentAllTokensBinding? = null

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val assetViewModel: AssetsLibraryViewModel by viewModels {
        factory
    }
    private val collectionViewModel: CollectionViewModel by viewModels {
        factory
    }
    private val currentUserViewModel: CurrentUserViewModel by viewModels {
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
        currentUserViewModel.getUser()
        setupScreen()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.run {
            btnAddToken.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .add(AddTokenFragment(), "ADD_TOKEN")
                    .commit()
            }
            initObservers()
            initGenreRecyclerView()
        }
    }

    private fun initObservers() {
        with(assetViewModel) {
            assetListCheap.observe(viewLifecycleOwner) { t ->
                observing(t, 1)
            }
            assetList.observe(viewLifecycleOwner) { t ->
                observing(t, 2)
            }
        }
        collectionViewModel.collectionList.observe(viewLifecycleOwner) { c ->
            if (c != null) {
                initCollectionRecyclerView(c)
            } else binding?.slCollections?.visibility = View.VISIBLE
        }
        currentUserViewModel.currentUser.observe(viewLifecycleOwner) {
            changeButtonVisibility(it?.nickname == null)
        }
    }

    private fun initGenreRecyclerView() {
        binding?.rvGenres?.apply {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = RecyclerView.HORIZONTAL
            }
            adapter = GenreCollectionAdapter().apply {
                setItemViewCacheSize(10)
                onClick = {
                    findNavController().apply {
                        popBackStack()
                        navigate(R.id.searchFragment).apply {
                            SearchFragment.category = it
                        }
                    }
                }
            }
        }
    }

    private fun initCollectionRecyclerView(list: List<InfoCollection>?) {
        binding?.run {
            rvCollections.apply {
                layoutManager = LinearLayoutManager(context).apply {
                    orientation = RecyclerView.HORIZONTAL
                }
                if (list != null) {
                    val briefList = arrayListOf<CollectionBrief>()
                    for (collection in list) {
                        briefList.add(collection.toCollectionBrief())
                    }
                    adapter = CollectionAdapter(briefList).apply {
                        onClick = { collection ->
                            swapCollectionInfoBottomSheet(
                                list.first(predicate = {
                                    it.bannerImageUrl == collection?.bannerImageUrl &&
                                            it.username == collection?.username
                                })
                            )
                        }
                    }
                }
                initRecycler(slCollections, rvCollections)
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
            initRecycler(slGenres, rvGenres)
            with(assetViewModel) {
                getAssetsCheap()
                getTenAssets()
            }
            collectionViewModel.getCollections()
        }
    }

    private fun initTokensRecyclerView(
        rv: RecyclerView,
        shimmer: ShimmerFrameLayout,
        list: List<InfoAsset>?
    ) {
        val briefList = arrayListOf<AssetBrief>()
        list?.forEach(action = {
            briefList.add(it.toAssetBrief())
        })
        rv.apply {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = RecyclerView.HORIZONTAL
            }
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
            }, "ASSET")
            .commit()
    }

    private fun swapCollectionInfoBottomSheet(collection: InfoCollection?) {
        val bundle = Bundle().apply {
            putSerializable("collection", collection)
        }
        parentFragmentManager.beginTransaction()
            .add(CollectionInfoFragment().apply {
                arguments = bundle
            }, "COLLECTION")
            .commit()
    }

    private fun changeButtonVisibility(b: Boolean) {
        binding?.btnAddToken?.visibility = if (b) View.GONE else View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        assetViewModel.close()
        collectionViewModel.close()
        binding = null
    }

}
