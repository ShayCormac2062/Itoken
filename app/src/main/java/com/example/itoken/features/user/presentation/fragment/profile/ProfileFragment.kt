package com.example.itoken.features.user.presentation.fragment.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
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
import com.example.itoken.features.user.domain.model.UserModel
import com.example.itoken.features.user.presentation.adapter.TokenAdapter
import com.example.itoken.features.user.presentation.model.ItemAssetBrief
import com.example.itoken.features.user.presentation.viewmodel.AssetViewModel
import com.example.itoken.features.user.presentation.viewmodel.UsersViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileFragment : BottomSheetDialogFragment() {

    private var binding: FragmentProfileBinding? = null

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val assetViewModel: AssetViewModel by viewModels {
        factory
    }
    private val usersViewModel: UsersViewModel by viewModels {
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
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        binding?.run {
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

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            usersViewModel.getUser()
        }
    }

    private fun clickCard(rv: RecyclerView, iv: ImageView, isActivated: Boolean, point: Int) {
        if (isActivated) {
            iv.load(R.drawable.arrow_down)
            rv.adapter = null
        } else lifecycleScope.launch {
            iv.load(R.drawable.arrow_up)
            when (point) {
                1 -> assetViewModel.getCollected(binding?.tvCreatorName?.text.toString())
                2 -> assetViewModel.getCreated(binding?.tvCreatorName?.text.toString())
                else -> assetViewModel.getFavourites(binding?.tvCreatorName?.text.toString())
            }
        }
        rv.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                if (isActivated) R.anim.swipe_up else R.anim.swipe_down
            )
        )
    }

    private fun initObservers() {
        binding?.run {
            with(assetViewModel) {
                collectedAssetList.observe(viewLifecycleOwner) { t ->
                    initTokensRecyclerView(rvCollected, t)
                }
                createdAssetList.observe(viewLifecycleOwner) { t ->
                    initTokensRecyclerView(rvCreated, t)
                }
                favouritesAssetList.observe(viewLifecycleOwner) { t ->
                    initTokensRecyclerView(rvFavourite, t)
                }
                collectedAssetListAmount.observe(viewLifecycleOwner) {
                    binding?.tvCollected?.text = "Куплено: ${it}"
                }
                createdAssetListAmount.observe(viewLifecycleOwner) {
                    binding?.tvCreated?.text = "Создано: ${it}"
                }
                favouritesAssetListAmount.observe(viewLifecycleOwner) {
                    binding?.tvFavourite?.text = "Избранное: ${it}"
                }
            }
            with(usersViewModel) {
                currentUser.observe(viewLifecycleOwner) { user ->
                    setupScreen(user)
                    Log.e("MY_USER", user.toString())
                }
            }
        }
    }

    private fun setupScreen(user: UserModel?) {
        binding?.run {
            imageView.load(user?.imageUrl)
            ivCreatorAvatar.load(user?.imageUrl)
            tvDescription.text = user?.description
            tvCreatorAddress.text = user?.stringId
            tvCreatorName.text = user?.nickname
            tvCrystalAmount.text = "${user?.balance?.toInt()} ICrystal"
            lifecycleScope.launch {
                with(assetViewModel) {
                    getCollectedAmount(user?.nickname.toString())
                    getCreatedAmount(user?.nickname.toString())
                    getFavouritesAmount(user?.nickname.toString())
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
        lifecycleScope.launch {
            assetViewModel.closePage()
            usersViewModel.closePage()
        }
        binding = null
    }
}
