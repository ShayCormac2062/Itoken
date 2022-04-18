package com.example.itoken.features.user.presentation.fragment.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.itoken.App
import com.example.itoken.MainActivity
import com.example.itoken.R
import com.example.itoken.databinding.FragmentLoadingBinding
import com.example.itoken.features.user.presentation.viewmodel.AssetViewModel
import com.example.itoken.features.user.presentation.viewmodel.UsersViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoadingFragment : Fragment() {

    private var binding: FragmentLoadingBinding? = null

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
        binding = FragmentLoadingBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        lifecycleScope.launch {
            usersViewModel.getUser()
        }
    }

    private fun initObservers() {
        usersViewModel.currentUser.observe(viewLifecycleOwner) {
            it?.fold(onSuccess = { user ->
                if (user != null) {
                    (requireActivity() as MainActivity).changeButtonVisibility(false)
                    findNavController().navigate(R.id.profileFragment)
                } else findNavController().navigate(R.id.loginFragment)
            }, onFailure = { error ->
                Log.e("FUCK", error.message.toString())
            })
        }
    }
}