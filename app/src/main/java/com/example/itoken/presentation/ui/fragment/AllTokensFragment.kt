package com.example.itoken.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itoken.data.AssetRepositoryImpl
import com.example.itoken.data.entity.Asset
import com.example.itoken.data.retrofit.di.DIContainer
import com.example.itoken.databinding.FragmentAllTokensBinding
import com.example.itoken.domain.repository.AssetRepository
import com.example.itoken.presentation.adapter.CollectionAdapter
import com.example.itoken.presentation.adapter.GenreCollectionAdapter
import com.example.itoken.presentation.adapter.TokenAdapter
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.launch

class AllTokensFragment : Fragment() {

    private var binding: FragmentAllTokensBinding? = null
    private var repository: AssetRepository? = AssetRepositoryImpl()
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
            rvGenres.apply {
                layoutManager = LinearLayoutManager(context).apply {
                    orientation = RecyclerView.HORIZONTAL
                }
                adapter = GenreCollectionAdapter().apply {
                    onClick = {
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

    private fun setupScreen() {
        lifecycleScope.launch {
            binding?.run {
                slGenres.startShimmer()
                slTokens.startShimmer()
                slCheapTokens.startShimmer()
                tokens = repository?.getAssetsBrief()
                cheapTokens = repository?.getAssetsBrief()
                initTokensRecyclerView(rvTokens, slTokens, tokens)
                initTokensRecyclerView(rvCheapTokens, slCheapTokens, cheapTokens)
                initRecycler(slGenres, rvGenres)
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

    private fun swapTokenInfoBottomSheet(asset: Asset?, likes: Int?) {
        parentFragmentManager.beginTransaction()
            .add(TokenInfoFragment(asset, likes), "FUCK")
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        repository = null
        tokens = null
        cheapTokens = null
        collections = null
    }
}