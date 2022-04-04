package com.example.itoken.features.profile.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.itoken.App
import com.example.itoken.R
import com.example.itoken.databinding.FragmentProfileBinding
import com.example.itoken.features.addtoken.presentation.viewmodel.AddTokenViewModel
import javax.inject.Inject

class ProfileFragment: Fragment() {

    private var binding: FragmentProfileBinding? = null

//    TODO(потом либо создай ещё один модуль для бд, либо объедини эти 2 фичи)
//    @Inject
//    lateinit var factory: ViewModelProvider.Factory
//    private val viewModel: AddTokenViewModel by viewModels {
//        factory
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        App.appComponent.inject(this)
//        super.onCreate(savedInstanceState)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run {
            btnGoToClicker.setOnClickListener {
                activity?.findNavController(R.id.fragmentContainerView)
                    ?.navigate(R.id.clickerFragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
