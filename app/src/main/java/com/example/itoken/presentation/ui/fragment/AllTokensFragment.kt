package com.example.itoken.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itoken.App
import com.example.itoken.data.response.Asset
import com.example.itoken.databinding.FragmentAllTokensBinding
import com.example.itoken.presentation.adapter.CollectionAdapter
import com.example.itoken.presentation.adapter.GenreCollectionAdapter
import com.example.itoken.presentation.adapter.TokenAdapter
import com.example.itoken.presentation.ui.activity.MainActivity
import com.example.itoken.presentation.viewmodel.MainViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllTokensFragment : Fragment() {

    private var binding: FragmentAllTokensBinding? = null
    private var collections: List<Asset>? = null //TODO(сделай api для коллекций, даун!!!)
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllTokensBinding.inflate(layoutInflater)
        viewModel = (requireActivity() as MainActivity).viewModel
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
                        initTokensRecyclerView(rvCheapTokens, slCheapTokens, t)
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
        list: List<Asset>?
    ) {
        rv.apply {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = RecyclerView.HORIZONTAL
            }
            adapter = TokenAdapter(list, context).apply {
                onClick = { asset, likes ->
                    swapTokenInfoBottomSheet(asset, likes)
                }
                setItemViewCacheSize(20)
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
            .add(TokenInfoFragment(asset, likes), "SHIT")
            .commit()
    }

}