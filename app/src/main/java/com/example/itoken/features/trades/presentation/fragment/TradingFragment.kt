package com.example.itoken.features.trades.presentation.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import coil.load
import com.example.itoken.App
import com.example.itoken.R
import com.example.itoken.common.viewmodel.CurrentUserViewModel
import com.example.itoken.databinding.FragmentTradingBinding
import com.example.itoken.features.trades.domain.model.TradeModel
import com.example.itoken.features.user.domain.model.UserModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class TradingFragment : Fragment() {

    private var binding: FragmentTradingBinding? = null
    private var trade: TradeModel? = null
    private var currentUser: UserModel? = null

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val currentUserViewModel: CurrentUserViewModel by viewModels {
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
        lifecycleScope.launch {
            currentUserViewModel.getUser()
        }
        binding?.run {
            init()
            btnShowMembers.setOnClickListener {
                showBottomSheet()
            }
            btnEnd.setOnClickListener {
                activity?.findNavController(R.id.fragmentContainerView)
                    ?.navigate(R.id.tradeFragment)
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
        binding = null
    }

    private fun init() {
        binding?.run {
            ivTokenPicture.load(Uri.parse(trade?.token?.imageUrl))
            tvTokenName.text = trade?.token?.tokenName
            tvCreatorName.text = trade?.author
            tvPrice.text = "Минимальная цена: ${trade?.price ?: 0} ICrystal"
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
            }, "SHIT")
            .commit()
    }
}
