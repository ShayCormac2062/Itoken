package com.example.itoken.features.user.presentation.fragment.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.itoken.App
import com.example.itoken.R
import com.example.itoken.common.fragment.TokenInfoFragment
import com.example.itoken.databinding.FragmentProfileBinding
import com.example.itoken.features.user.domain.model.ItemAsset
import com.example.itoken.features.user.presentation.adapter.TokenAdapter
import com.example.itoken.features.user.presentation.model.ItemAssetBrief
import com.example.itoken.features.user.presentation.viewmodel.AssetViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileFragment : BottomSheetDialogFragment() {

    private var binding: FragmentProfileBinding? = null

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: AssetViewModel by viewModels {
        factory
    }
    private var allList: List<ItemAsset>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        binding?.run {
            lifecycleScope.launch {  viewModel.getAll() }
            btnGoToClicker.setOnClickListener {
                activity?.findNavController(R.id.fragmentContainerView)
                    ?.navigate(R.id.clickerFragment)
            }
            cardCollected.setOnClickListener {
                clickCard(rvCollected, ivCollectedCard, it.isActivated, 1)
                it.isActivated = !it.isActivated
            }
            cardCreated.setOnClickListener {
                clickCard(rvCreated, ivCreatedCard, it.isActivated, 2)
                it.isActivated = !it.isActivated
            }
            cardFavourite.setOnClickListener {
                clickCard(rvFavourite, ivFavouriteCard, it.isActivated, 3)
                it.isActivated = !it.isActivated

            }
        }
    }

    private fun clickCard(rv: RecyclerView, iv: ImageView, isActivated: Boolean, point: Int) {
        if (isActivated) {
            iv.load(R.drawable.arrow_down)
            rv.adapter = null
        } else lifecycleScope.launch {
            iv.load(R.drawable.arrow_up)
            when (point) {
                1 -> viewModel.getCollected(binding?.tvCreatorName?.text.toString())
                2 -> viewModel.getCreated(binding?.tvCreatorName?.text.toString())
                else -> viewModel.getFavourites(binding?.tvCreatorName?.text.toString())
            }
        }
        rv.startAnimation(AnimationUtils.loadAnimation(context, if (isActivated) R.anim.swipe_up else R.anim.swipe_down))
    }

    private fun initObservers() {
        binding?.run {
            with(viewModel) {
                allAssetList.observe(viewLifecycleOwner) {
                    it?.fold(onSuccess = { t ->
                        tvCreated.text = "Собрано: ${t.size}"
                        allList = t
                    }, onFailure = { error ->
                        Log.e("FUCK", error.message.toString())
                    })
                }
                collectedAssetList.observe(viewLifecycleOwner) {
                    it?.fold(onSuccess = { t ->
                        initTokensRecyclerView(rvCollected, t)
                    }, onFailure = { error ->
                        Log.e("FUCK", error.message.toString())
                    })
                }
                createdAssetList.observe(viewLifecycleOwner) {
                    it?.fold(onSuccess = { t ->
                        initTokensRecyclerView(rvCreated, t)
                    }, onFailure = { error ->
                        Log.e("FUCK", error.message.toString())
                    })
                }
                favouritesAssetList.observe(viewLifecycleOwner) {
                    it?.fold(onSuccess = { t ->
                        initTokensRecyclerView(rvFavourite, t)
                    }, onFailure = { error ->
                        Log.e("FUCK", error.message.toString())
                    })
                }
            }
        }
    }

    private fun initTokensRecyclerView(
        rv: RecyclerView,
        list: List<ItemAsset>?
    ) {
        rv.apply {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = RecyclerView.HORIZONTAL
            }
            val briefList = arrayListOf<ItemAssetBrief>()
            list?.forEach(action = {
                briefList.add(it.toItemAssetBrief())
            })
            adapter = TokenAdapter(briefList).apply {
                onClick = { asset, likes ->
                    swapTokenInfoBottomSheet(
                        list?.first(
                            predicate = {
                                it.imageUrl == asset?.imageUrl
                            }
                        ) as ItemAsset, likes ?: 0)
                }
                setItemViewCacheSize(30)
            }
        }
    }
    private fun swapTokenInfoBottomSheet(asset: ItemAsset, likes: Int) {
        parentFragmentManager.beginTransaction()
            .add(TokenInfoFragment(asset, likes), "SHIT")
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
