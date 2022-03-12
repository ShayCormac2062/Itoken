package com.example.itoken.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.itoken.R
import com.example.itoken.databinding.FragmentTradingBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class TradingFragment : Fragment() {

    private var binding: FragmentTradingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTradingBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run {
            btnShowMembers.setOnClickListener {
                val sheet = LayoutInflater.from(context).inflate(R.layout.bottom_sheet, null)
                activity?.let { BottomSheetDialog(it) }?.apply {
                    setContentView(sheet)
                    show()
                }
            }
            btnEnd.setOnClickListener {
                activity?.findNavController(R.id.fragmentContainerView)
                    ?.navigate(R.id.tradeFragment)
            }
        }
    }

}