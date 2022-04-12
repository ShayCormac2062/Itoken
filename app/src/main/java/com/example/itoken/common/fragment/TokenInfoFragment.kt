package com.example.itoken.common.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.itoken.common.entity.BaseAsset
import com.example.itoken.databinding.FragmentTokenInfoBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TokenInfoFragment<M : BaseAsset>(private val asset: M, private val likes: Int) :
    BottomSheetDialogFragment() { //поменять получение из бандла

    private var binding: FragmentTokenInfoBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTokenInfoBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run { //переименовать все переменные из xml
            tokenInfoContainer.init(asset, likes)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
