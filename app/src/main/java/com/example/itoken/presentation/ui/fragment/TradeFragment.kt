package com.example.itoken.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itoken.R
import com.example.itoken.presentation.adapter.TradeAdapter
import com.example.itoken.databinding.FragmentTradeBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class TradeFragment : Fragment() {

    private var binding: FragmentTradeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTradeBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.run {
            btnCurrentTrades.setOnClickListener {
                //TODO
            }
            btnTradesHistory.setOnClickListener {
                //TODO
            }
            rvTrades.apply {
                layoutManager = LinearLayoutManager(context).apply {
                    orientation = RecyclerView.VERTICAL
                }
                adapter = TradeAdapter(null).apply {
                    onClick = {
                        activity?.findNavController(R.id.fragmentContainerView)
                            ?.navigate(R.id.tradingFragment)
                    }
                }
                ivNoTrades.visibility = View.INVISIBLE
                tvNoTrades.visibility = View.INVISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}