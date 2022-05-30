package com.example.itoken.features.trades.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.itoken.App
import com.example.itoken.R
import com.example.itoken.common.viewmodel.CurrentUserViewModel
import com.example.itoken.databinding.FragmentTradingBinding
import com.example.itoken.features.trades.domain.model.Auctioneer
import com.example.itoken.features.trades.domain.model.Lot
import com.example.itoken.features.trades.domain.model.TradeModel
import com.example.itoken.features.trades.presentation.viewmodel.TransactionViewModel
import com.example.itoken.features.user.domain.model.UserModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class TradingFragment : BottomSheetDialogFragment() {

    private var binding: FragmentTradingBinding? = null
    private var trade: TradeModel? = null
    private var currentUser: UserModel? = null

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val currentUserViewModel: CurrentUserViewModel by viewModels {
        factory
    }
    private val transactionViewModel: TransactionViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTradingBinding.inflate(layoutInflater)
        trade = arguments?.get("trade") as TradeModel
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        currentUserViewModel.getUser()
        binding?.run {
            init()
            btnShowMembers.setOnClickListener {
                showBottomSheet()
            }
        }
    }

    private fun initObservers() {
        currentUserViewModel.currentUser.observe(viewLifecycleOwner) {
            currentUser = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (auctioneer != null) {
            beginTransaction()
        }
        auctioneer = null
        lot = null
        binding = null
    }

    private fun beginTransaction() {
        with(transactionViewModel) {
            lot?.let { lot ->
                sendTokenToUser(
                    auctioneer?.stringId,
                    lot
                )
                closeTrade(lot.address)
                transferMoneyToBarker(currentUser?.stringId, auctioneer?.price?.toDouble())
            }
        }
    }

    private fun init() {
        binding?.run {
            ivTokenPicture.load(trade?.token?.imageUrl)
            tvTokenName.text = trade?.token?.tokenName
            tvCreatorName.text = trade?.author
            tvPrice.text = String.format(
                getString(R.string.minimal_price),
                trade?.price
            )
            if (trade?.isActive == true) {
                btnShowMembers.visibility = View.VISIBLE
            }
        }
    }

    private fun showBottomSheet() {
        val bundle = Bundle().apply {
            putSerializable("user", currentUser?.toUser())
            putSerializable("trade", trade)
        }
        childFragmentManager.beginTransaction()
            .add(MembersFragment().apply {
                arguments = bundle
            }, "MEMBERS")
            .commit()
    }

    companion object {
        var auctioneer: Auctioneer? = null
        var lot: Lot? = null
    }
}
