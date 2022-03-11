package com.example.itoken.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.itoken.R
import com.example.itoken.databinding.FragmentTokenInfoBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TokenInfoFragment : BottomSheetDialogFragment() {

    private var binding: FragmentTokenInfoBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTokenInfoBinding.inflate(layoutInflater)
        return binding?.root
    }
}