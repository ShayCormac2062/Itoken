package com.example.itoken.common.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.itoken.App
import com.example.itoken.common.entity.BaseAsset
import com.example.itoken.common.viewmodel.CurrentUserViewModel
import com.example.itoken.databinding.FragmentTokenInfoBinding
import com.example.itoken.features.addtoken.presentation.viewmodel.AddTokenViewModel
import com.example.itoken.features.trades.presentation.viewmodel.TradeViewModel
import com.example.itoken.features.user.domain.model.UserModel
import com.example.itoken.features.user.presentation.viewmodel.AssetViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class TokenInfoFragment : BottomSheetDialogFragment() {

    private var binding: FragmentTokenInfoBinding? = null
    private var currentUser: UserModel? = null
    private lateinit var asset: BaseAsset

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: AddTokenViewModel by viewModels {
        factory
    }
    private val usersViewModel: CurrentUserViewModel by viewModels {
        factory
    }
    private val assetViewModel: AssetViewModel by viewModels {
        factory
    }
    private val createTradeViewModel: TradeViewModel by viewModels {
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
        binding = FragmentTokenInfoBinding.inflate(layoutInflater)
        asset = arguments?.get("asset") as BaseAsset
        return binding?.root
    }

    private fun initObservers() {
        usersViewModel.currentUser.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                setupUser(user)
            }
            assetViewModel.getAll()
        }
        assetViewModel.allAssetList.observe(viewLifecycleOwner) { list ->
            if (list?.contains(asset) == true) {
                isUserBoughtThisAsset = true
            }
            binding?.run {
                tokenInfoContainer.init(
                    asset,
                    asset.likes?.toInt() ?: arguments?.getInt("likes") as Int,
                    isUserAuthorized,
                    isUserBoughtThisAsset
                )
            }
        }
    }

    private fun setupUser(user: UserModel) {
        currentUser = user
        isUserAuthorized = true
        if (user.nickname == asset.creatorName) {
            isUserCreator = true
        }
        currentUser?.balance?.let { balance ->
            asset.price?.let { price ->
                isUserHasEnoughMoney = balance.toInt() >= price.toDouble()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        usersViewModel.getUser()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        when {
            isNeedToTrade -> {
                createTradeViewModel.createTrade(asset.toLot())
            }
            isNeedToCollect -> {
                viewModel.add(asset.toAssetModel().apply {
                    ownerName = currentUser?.nickname
                })
                usersViewModel.changeBalance(asset.price?.toDouble()
                    ?.let { currentUser?.balance?.minus(it) })

            }
        }
        isUserAuthorized = false
        isUserBoughtThisAsset = false
        isNeedToCollect = false
        isNeedToTrade = false
        isUserHasEnoughMoney = false
        isUserCreator = false
        binding = null
    }

    companion object {
        var isUserAuthorized = false
        var isUserBoughtThisAsset = false
        var isNeedToCollect = false
        var isUserHasEnoughMoney = false
        var isUserCreator = false
        var isNeedToTrade = false
    }
}
