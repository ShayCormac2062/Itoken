package com.example.itoken.features.user.presentation.fragment.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.itoken.App
import com.example.itoken.R
import com.example.itoken.databinding.FragmentLoginBinding
import com.example.itoken.features.user.domain.model.UserModel
import com.example.itoken.features.user.presentation.viewmodel.AssetViewModel
import com.example.itoken.features.user.presentation.viewmodel.UsersViewModel
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
                    enter()
                } else makeToast("Введите все нужные данные")
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
            findNavController().navigate(R.id.loadingFragment)
        }
        usersViewModel.currentUser.observe(viewLifecycleOwner) { um ->
            if (um != null) {
                currentUser = um
                lifecycleScope.launch {
                    assetsViewModel.getAll()
                }
            } else makeToast("Ошибка в введённых данных. Проверьте их и повторите попытку")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        currentUser = null
    }

    private fun enter() {
        binding?.run {
            val isThisEmailOrNickname: Boolean = tietName.text.toString().contains('@')
            lifecycleScope.launch {
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
                usersViewModel.getUser()
            }
        }
    }

    private fun makeToast(message: String) = Toast.makeText(
        context,
        message,
        Toast.LENGTH_SHORT
    ).show()
}
