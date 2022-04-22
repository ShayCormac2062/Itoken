package com.example.itoken.features.trades.presentation.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import coil.load
import com.example.itoken.R
import com.example.itoken.databinding.FragmentTradingBinding
import com.example.itoken.features.trades.domain.model.Auctioneer
import com.example.itoken.features.trades.domain.model.TradeModel

class TradingFragment : Fragment() {

    private var binding: FragmentTradingBinding? = null
    private var trade: TradeModel? = null

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
        }
    }

    private fun showBottomSheet() {
        val bundle = Bundle().apply {
            putParcelableArrayList("candidates", trade?.candidates as ArrayList<Auctioneer>)
        }
        parentFragmentManager.beginTransaction()
            .add(MembersFragment().apply {
                arguments = bundle
            }, "SHIT")
            .commit()
    }
}
