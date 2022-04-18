package com.example.itoken.common.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.itoken.App
import com.example.itoken.common.entity.BaseAsset
import com.example.itoken.databinding.FragmentTokenInfoBinding
import com.example.itoken.features.addtoken.presentation.viewmodel.AddTokenViewModel
import com.example.itoken.features.addtoken.presentation.viewmodel.CurrentUserViewModel
import com.example.itoken.features.user.domain.model.UserModel
import com.example.itoken.features.user.presentation.viewmodel.AssetViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class TokenInfoFragment :
    BottomSheetDialogFragment() { //поменять получение из бандла

    private var binding: FragmentTokenInfoBinding? = null
    private var currenUser: UserModel? = null

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

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTokenInfoBinding.inflate(layoutInflater)
        isNeedToCollect = false
        isNeedToFavourites = false
        return binding?.root
    }

    private fun initObservers() {
        usersViewModel.currentUser.observe(viewLifecycleOwner) {
            it?.fold(onSuccess = { user ->
                currenUser = user
                isUserAuthorized = true
                currenUser?.balance?.let { balance ->
                    asset.price?.let { price ->
                        isUserHasEnoughMoney = balance.toInt() >= price.toDouble()
                    }
                }
                lifecycleScope.launch {
                    assetViewModel.getAll()
                }
                Log.e("MY_USER_AT_TOKEN_INFO", user.toString())
            }, onFailure = { error ->
                Log.e("FUCK", error.message.toString())
            })
        }
        assetViewModel.allAssetList.observe(viewLifecycleOwner) {
            it?.fold(onSuccess = { list ->
                if (list.contains(asset)) {
                    isUserBoughtThisAsset = true
                }
                binding?.run {
                    tokenInfoContainer.init(asset, likes, isUserAuthorized, isUserBoughtThisAsset)
                }
            }, onFailure = { error ->
                Log.e("FUCK", error.message.toString())
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        lifecycleScope.launch {
            usersViewModel.getUser()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (isNeedToCollect) {
            viewModel.add(asset.toAssetModel().apply {
                ownerName = currenUser?.nickname
            })
            lifecycleScope.launch {
                usersViewModel.changeBalance(asset.price?.toDouble()
                    ?.let { currenUser?.balance?.minus(it) })
                println(currenUser?.toString())
            }
        } else if (isNeedToFavourites) {
            viewModel.add(asset.toAssetModel())
        }
        likes = 0
        isUserAuthorized = false
        isUserBoughtThisAsset = false
        isNeedToCollect = false
        isNeedToFavourites = false
        isUserHasEnoughMoney = false
        binding = null
    }

    companion object {
        lateinit var asset: BaseAsset
        var likes = 0
        var isUserAuthorized = false
        var isUserBoughtThisAsset = false
        var isNeedToCollect = false
        var isNeedToFavourites = false
        var isUserHasEnoughMoney = false
    }
}
