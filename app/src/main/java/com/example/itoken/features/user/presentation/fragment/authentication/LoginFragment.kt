package com.example.itoken.features.user.presentation.fragment.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.itoken.App
import com.example.itoken.R
import com.example.itoken.databinding.FragmentLoginBinding
import com.example.itoken.features.user.domain.model.UserModel
import com.example.itoken.features.user.presentation.viewmodel.AssetViewModel
import com.example.itoken.features.user.presentation.viewmodel.UsersViewModel
import com.example.itoken.utils.CommonUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null
    private var currentUser: UserModel? = null

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val usersViewModel: UsersViewModel by viewModels {
        factory
    }
    private val assetsViewModel: AssetViewModel by viewModels {
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
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        binding?.run {
            tvRegister.apply {
                text = android.text.Html.fromHtml("<u>Зарегистрируйтесь</u>")
                setOnClickListener {
                    activity?.findNavController(R.id.fragmentContainerView)
                        ?.navigate(R.id.registrationFragment)
                }
            }
            btnEnter.setOnClickListener {
                if (tietName.text.toString() != "" && tietPassword.text.toString() != "") {
                    setupElementsVisibility(false)
                    enter()
                } else CommonUtils.makeToast(
                    context,
                    getString(R.string.empty_fields)
                )
            }
        }
    }

    private fun initObservers() {
        assetsViewModel.allAssetList.observe(viewLifecycleOwner) { t ->
            if (t?.isEmpty() == true) {
                lifecycleScope.launch {
                    currentUser?.assets?.let { it1 -> assetsViewModel.addAll(it1) }
                }
            }
            activity?.findNavController(R.id.fragmentContainerView)
                ?.navigate(R.id.profileFragment)
        }
        usersViewModel.currentUser.observe(viewLifecycleOwner) { um ->
            if (um?.imageUrl != null) {
                currentUser = um
                assetsViewModel.getAll()
            } else if (um != null) {
                setupElementsVisibility(true)
                CommonUtils.makeToast(
                    context,
                    getString(R.string.wrong_fields)
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        enableNavigationButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        currentUser = null
    }

    private fun enableNavigationButton() {
        activity?.findViewById<BottomNavigationView>(R.id.bottom_main)
            ?.menu
            ?.getItem(3)
            ?.isChecked = true
    }

    private fun enter() {
        binding?.run {
            val isThisEmailOrNickname: Boolean = tietName.text.toString().contains('@')
            usersViewModel.addUser(
                UserModel(
                    null,
                    null,
                    if (!isThisEmailOrNickname) tietName.text.toString() else null,
                    null,
                    tietPassword.text.toString(),
                    if (isThisEmailOrNickname) tietName.text.toString() else null,
                    null,
                    null
                )
            )
        }
    }

    private fun setupElementsVisibility(isUserGot: Boolean) {
        binding?.run {
            pbLoading.visibility = if (isUserGot) View.INVISIBLE else View.VISIBLE
            tilName.visibility = if (!isUserGot) View.INVISIBLE else View.VISIBLE
            tilPassword.visibility = if (!isUserGot) View.INVISIBLE else View.VISIBLE
            btnEnter.visibility = if (!isUserGot) View.INVISIBLE else View.VISIBLE
            tvNoAccount.visibility = if (!isUserGot) View.INVISIBLE else View.VISIBLE
            tvRegister.visibility = if (!isUserGot) View.INVISIBLE else View.VISIBLE
        }
    }

}
