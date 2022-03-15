package com.example.itoken.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.itoken.R
import com.example.itoken.databinding.FragmentClickerBinding

class ClickerFragment : Fragment() {

    private var binding: FragmentClickerBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClickerBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run {
            btnEnd.setOnClickListener {
                activity?.findNavController(R.id.fragmentContainerView)
                    ?.navigate(R.id.profileFragment)
            }
            ivCrystalAnim.setOnClickListener {
                //
            }
        }
    }
}