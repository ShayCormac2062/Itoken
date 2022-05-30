package com.example.itoken.features.trades.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itoken.App
import com.example.itoken.databinding.FragmentTradeBinding
import com.example.itoken.features.trades.domain.model.TradeModel
import com.example.itoken.features.trades.presentation.adapter.TradeAdapter
import com.example.itoken.features.trades.presentation.viewmodel.TradeViewModel
import javax.inject.Inject

class TradeFragment : Fragment() {

    private var binding: FragmentTradeBinding? = null

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: TradeViewModel by viewModels {
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
        binding = FragmentTradeBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initObservers()
        viewModel.getAllTrades()
        binding?.run {
            srlUpdate.setOnRefreshListener {
                rvTrades.adapter = null
                pbLoading.visibility = View.VISIBLE
                viewModel.getAllTrades()
                srlUpdate.isRefreshing = false
            }
        }
    }

    private fun initObservers() {
        with(viewModel) {
            allTradeList.observe(viewLifecycleOwner) {
                if (it?.isNotEmpty() == true || it == null) {
                    binding?.pbLoading?.visibility = View.INVISIBLE
                    initRecyclerView(it)
                } else notifyNoTrades()
            }
        }
    }

    private fun notifyNoTrades() {
        binding?.run {
            ivNoTrades.visibility = View.VISIBLE
            tvNoTrades.visibility = View.VISIBLE
            pbLoading.visibility = View.INVISIBLE
        }
    }

    private fun initRecyclerView(list: List<TradeModel>?) {
        binding?.run {
            rvTrades.apply {
                layoutManager = LinearLayoutManager(context).apply {
                    orientation = RecyclerView.VERTICAL
                }
                adapter = TradeAdapter(list).apply {
                    onClick = {
                        swapTradingFragment(it)
                    }
                }
            }
            pbLoading.visibility = View.INVISIBLE
            ivNoTrades.visibility = View.INVISIBLE
            tvNoTrades.visibility = View.INVISIBLE
        }
    }

    private fun swapTradingFragment(tradeModel: TradeModel?) {
        val bundle = Bundle().apply {
            putSerializable("trade", tradeModel)
        }
        parentFragmentManager.beginTransaction()
            .add(TradingFragment().apply {
                arguments = bundle
            }, "TRADING")
            .commit()
    }
}
