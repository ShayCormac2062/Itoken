package com.example.itoken.features.user.presentation.fragment.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.itoken.App
import com.example.itoken.R
import com.example.itoken.databinding.FragmentClickerBinding
import com.example.itoken.features.user.presentation.viewmodel.UsersViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import javax.inject.Inject

class ClickerFragment : Fragment() {

    private var binding: FragmentClickerBinding? = null

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val usersViewModel: UsersViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClickerBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        binding?.run {
            lifecycleScope.launch {
                usersViewModel.getUser()
            }
            btnEnd.setOnClickListener {
                activity?.findNavController(R.id.fragmentContainerView)
                    ?.navigate(R.id.profileFragment)
            }
            ivCrystalAnim.setOnClickListener {
                it.startAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_up))
                tvUserCrystals.text = (tvUserCrystals.text.toString().toDouble() + 0.25f).toString()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        enableNavigationButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lifecycleScope.launch {
            usersViewModel.changeBalance(binding?.tvUserCrystals?.text.toString().toDouble())
        }
        binding = null
    }

    private fun initObservers() {
        usersViewModel.currentUser.observe(viewLifecycleOwner) { user ->
            binding?.tvUserCrystals?.text = user?.balance?.toString()
        }
    }

    private fun enableNavigationButton() {
        activity?.findViewById<BottomNavigationView>(R.id.bottom_main)
            ?.menu
            ?.getItem(3)
            ?.isChecked = true
    }
}
