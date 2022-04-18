package com.example.itoken.features.assetlibrary.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.itoken.databinding.FragmentNoConnectionBinding

class NoConnectionFragment : Fragment() {

    private var binding: FragmentNoConnectionBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoConnectionBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.updateButton?.setOnClickListener {
            findNavController().navigate(previousFragment)
        }
    }

    companion object {
        var previousFragment: Int = 0
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}