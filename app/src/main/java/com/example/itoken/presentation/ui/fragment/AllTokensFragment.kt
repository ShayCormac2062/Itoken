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
import com.example.itoken.R
import com.example.itoken.data.entity.Asset
import com.example.itoken.databinding.FragmentAllTokensBinding
import com.example.itoken.data.AssetRepositoryImpl
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
        //TODO сделать получение из api
        binding = FragmentAllTokensBinding.inflate(layoutInflater)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.run {
            slTokens.startShimmer()
            slCheapTokens.startShimmer()
            lifecycleScope.launch {
                rvGenres.apply {
                    layoutManager = LinearLayoutManager(context).apply {
                        orientation = RecyclerView.HORIZONTAL
                    }
                    adapter = GenreCollectionAdapter().apply {
                        onClick = {
                        }
                    }
                }
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
                onClick = {
                    swapTokenInfoBottomSheet()
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

    private fun swapTokenInfoBottomSheet() {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_token_info, null)
//        val bindingForSheet = FragmentTokenInfoBinding.inflate(LayoutInflater.from(context))
//        with(bindingForSheet) {
//
//        }
        activity?.let { BottomSheetDialog(it) }?.apply {
            setContentView(view)
            show()
        }
    }
}