package com.example.itoken.features.user.presentation.fragment.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
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
import com.example.itoken.utils.CommonUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class ProfileFragment : BottomSheetDialogFragment() {

    private var binding: FragmentProfileBinding? = null
    private var myUser: UserModel? = null

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
            ivCreatorAvatar.setOnClickListener {
                CommonUtils.showDialog(
                    context,
                    getString(R.string.change_photo_question),
                    onClickEvent = {
                        selectImageFromGallery()
                    }
                )
            }
            btnSignOut.setOnClickListener {
                CommonUtils.showDialog(
                    context,
                    getString(R.string.sign_out),
                    onClickEvent = {
                        activity?.findNavController(R.id.fragmentContainerView)
                            ?.navigate(R.id.loginFragment)
                        usersViewModel.signOut()
                    }
                )
            }
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
            cardTraded.setOnClickListener {
                clickCard(rvTraded, ivTradedCard, it.isActivated, 3)
                it.isActivated = !it.isActivated
            }
            collectedCardview.setOnClickListener {
                cardCollected.performClick()
            }
            createdCardview.setOnClickListener {
                cardCreated.performClick()
            }
            tradedCardview.setOnClickListener {
                cardTraded.performClick()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        usersViewModel.getUser()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 100) {
                val uri = data?.data.toString()
                binding?.ivCreatorAvatar?.load(uri)
                binding?.imageView?.load(uri)
                usersViewModel.changePhoto(uri)
                CommonUtils.makeToast(
                    context,
                    getString(R.string.photo_changed)
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        assetViewModel.closePage()
        binding = null
    }

    private fun clickCard(rv: RecyclerView, iv: ImageView, isActivated: Boolean, point: Int) {
        if (isActivated) {
            iv.load(R.drawable.arrow_down)
            rv.adapter = null
        } else {
            iv.load(R.drawable.arrow_up)
            when (point) {
                1 -> assetViewModel.getCollected(binding?.tvCreatorName?.text.toString())
                2 -> assetViewModel.getCreated(binding?.tvCreatorName?.text.toString())
                else -> assetViewModel.getTraded(
                    binding?.tvCreatorName?.text.toString(),
                    myUser?.stringId
                )
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
                tradedAssetList.observe(viewLifecycleOwner) { t ->
                    initTokensRecyclerView(rvTraded, t)
                }
                collectedAssetListAmount.observe(viewLifecycleOwner) {
                    binding?.tvCollected?.text = String.format(
                        getString(R.string.bought_amount),
                        it
                    )
                }
                createdAssetListAmount.observe(viewLifecycleOwner) {
                    binding?.tvCreated?.text = String.format(
                        getString(R.string.created_amount),
                        it
                    )
                }
                tradedAssetListAmount.observe(viewLifecycleOwner) {
                    binding?.tvTraded?.text = String.format(
                        getString(R.string.traded_amount),
                        it
                    )
                }
            }
            with(usersViewModel) {
                currentUser.observe(viewLifecycleOwner) { user ->
                    myUser = user
                    setupScreen(user)
                }
            }
        }
    }

    private fun setupScreen(user: UserModel?) {
        binding?.run {
            imageView.load(
                Uri.parse(
                    user?.imageUrl
                )
            )
            ivCreatorAvatar.load(Uri.parse(user?.imageUrl))
            tvDescription.text = user?.description
            tvCreatorAddress.text = user?.stringId
            tvCreatorName.text = user?.nickname
            tvCrystalAmount.text = String.format(
                getString(R.string.balance_amount),
                user?.balance?.toInt()
            )
            with(assetViewModel) {
                getCollectedAmount(user?.nickname.toString())
                getCreatedAmount(user?.nickname.toString())
                getTradedAmount(user?.nickname.toString(), user?.stringId)
            }
        }
    }

    private fun initTokensRecyclerView(
        rv: RecyclerView,
        list: List<ItemAsset>?
    ) {
        val briefList = arrayListOf<ItemAssetBrief>()
        list?.forEach(action = {
            briefList.add(it.toItemAssetBrief())
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
            }, "ASSET")
            .commit()
    }

    private fun selectImageFromGallery() {
        val i = Intent(Intent.ACTION_PICK)
        i.type = "image/*"
        startActivityForResult(i, 100)
    }

}
