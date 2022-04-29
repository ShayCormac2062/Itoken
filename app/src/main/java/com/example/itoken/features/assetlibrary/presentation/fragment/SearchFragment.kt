package com.example.itoken.features.assetlibrary.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itoken.App
import com.example.itoken.R
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import com.example.itoken.common.fragment.TokenInfoFragment
import com.example.itoken.databinding.FragmentSearchBinding
import com.example.itoken.features.assetlibrary.domain.model.InfoAsset
import com.example.itoken.features.assetlibrary.presentation.adapter.GenreCollectionAdapter
import com.example.itoken.features.assetlibrary.presentation.adapter.TokenAdapter
import com.example.itoken.features.assetlibrary.presentation.model.AssetBrief
import com.example.itoken.features.assetlibrary.presentation.viewmodel.SearchAssetViewModel
import javax.inject.Inject

class SearchFragment : Fragment() {

    private var binding: FragmentSearchBinding? = null

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: SearchAssetViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initObservers()
        setupScreen(true)
        binding?.run {
            search.setOnQueryTextListener(object : OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.close()
                    setupScreen(false)
                    viewModel.getSearchResponse(query.toString())
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }

            })
            rvGenres.apply {
                layoutManager = LinearLayoutManager(context).apply {
                    orientation = RecyclerView.HORIZONTAL
                }
                adapter = GenreCollectionAdapter().apply {
                    onClick = {
                        category = it
                        tvPopularTokens.text = "Результаты по запросу: \"${category}\""
                        setupScreen(true)
                    }
                }
            }
        }
    }

    private fun initObservers() {
        with (viewModel) {
            allAssetList.observe(viewLifecycleOwner) { list ->
                if (list?.isNotEmpty() == true) {
                    binding?.tvPopularTokens?.text = "Популярные токены"
                    initRecycler(list)
                } else if (list != null) {
                    findNavController().navigate(R.id.noConnectionFragment).apply {
                        NoConnectionFragment.previousFragment = R.id.searchFragment
                    }
                }
            }
            categoryAssetList.observe(viewLifecycleOwner) { list ->
                if (list?.isNotEmpty() == true) {
                    initRecycler(list)
                }
            }
            searchAssetList.observe(viewLifecycleOwner) { list ->
                if (list?.isNotEmpty() == true) {
                    binding?.tvPopularTokens?.text = "Результаты поиска"
                    initRecycler(list)
                }
            }
        }
    }

    private fun initRecycler(list: List<InfoAsset>?) {
        binding?.rvPopularTokens?.apply {
            layoutManager = GridLayoutManager(context, 2).apply {
                orientation = RecyclerView.VERTICAL
            }
            setItemViewCacheSize(30)
            val briefList = arrayListOf<AssetBrief>()
            list?.forEach(action = {
                briefList.add(it.toAssetBrief())
            })
            adapter = TokenAdapter(briefList).apply {
                onClick = { asset, likes ->
                    swapTokenInfoBottomSheet(list?.first(
                        predicate = {
                            it.imageUrl == asset?.imageUrl &&
                                    it.likes == likes
                        }
                    ) as InfoAsset, likes.toInt())
                }
            }
        }
        finish()
    }

    private fun setupScreen(check: Boolean) {
        binding?.run {
            rvPopularTokens.visibility = View.INVISIBLE
            slTokens.visibility = View.VISIBLE
            slTokens.startShimmer()
            slTokens2.visibility = View.VISIBLE
            slTokens2.startShimmer()
            if (check) {
                category?.let {
                    viewModel.getCategoryResponse(category.toString())
                } ?: viewModel.getAllAssets()
            }
        }
    }

    private fun finish() {
        binding?.run {
            slTokens.stopShimmer()
            slTokens.visibility = View.GONE
            slTokens2.stopShimmer()
            slTokens2.visibility = View.GONE
            rvPopularTokens.visibility = View.VISIBLE
        }
    }

    private fun swapTokenInfoBottomSheet(asset: InfoAsset, likes: Int) {
        val bundle = Bundle().apply {
            putSerializable("asset", asset)
            putInt("likes", likes)
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
        category = null
    }

    companion object {
        var category: String? = null
    }
}
