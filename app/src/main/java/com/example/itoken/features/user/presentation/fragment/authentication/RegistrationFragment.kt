package com.example.itoken.features.user.presentation.fragment.authentication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.itoken.App
import com.example.itoken.R
import com.example.itoken.databinding.FragmentRegistrationBinding
import com.example.itoken.features.user.domain.model.UserModel
import com.example.itoken.features.user.presentation.viewmodel.UsersViewModel
import com.example.itoken.utils.CommonUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import javax.inject.Inject

class RegistrationFragment : Fragment() {

    private var binding: FragmentRegistrationBinding? = null
    private var imageUrl: String = ""

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val usersViewModel: UsersViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(layoutInflater)
        initObservers()
        return binding?.root
    }

    private fun initObservers() {
        usersViewModel.isUserExists.observe(viewLifecycleOwner) {
            if (it == true) {
                findNavController().navigate(R.id.loadingFragment)
            } else {
                setupVisibility(false)
                CommonUtils.makeToast(context, getString(R.string.user_exists))
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run {
            btnRegister.setOnClickListener {
                if (isNoEmptyFields()) {
                    registerUser()
                } else CommonUtils.makeToast(context, getString(R.string.empty_fields))
            }
            btnGetPhoto.setOnClickListener {
                selectImageFromGallery()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 100) {
                imageUrl = data?.data.toString()
                binding?.btnGetPhoto?.isActivated = true
                binding?.btnGetPhoto?.text = getString(R.string.photo_selected)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        enableNavigationButton()
    }

    private fun enableNavigationButton() {
        activity?.findViewById<BottomNavigationView>(R.id.bottom_main)
            ?.menu
            ?.getItem(3)
            ?.isChecked = true
    }

    private fun registerUser() {
        binding?.run {
            if (tietPassword.text.toString() == tietRepeatPassword.text.toString()) {
                setupVisibility(true)
                usersViewModel.registerUser(
                        UserModel(
                            generateString(),
                            imageUrl,
                            tietNickname.text.toString(),
                            tietDescription.text.toString(),
                            tietPassword.text.toString(),
                            tietEmail.text.toString(),
                            arrayListOf(),
                            1250.00
                        )
                    )
            } else CommonUtils.makeToast(context, getString(R.string.not_same_passwords))
        }
    }

    private fun setupVisibility(isUserNew: Boolean) {
        binding?.run {
            pbLoading.visibility = if (isUserNew) View.VISIBLE else View.INVISIBLE
            tilNickname.visibility = if (isUserNew) View.INVISIBLE else View.VISIBLE
            tilDescription.visibility = if (isUserNew) View.INVISIBLE else View.VISIBLE
            tilEmail.visibility = if (isUserNew)View.INVISIBLE else View.VISIBLE
            tilPassword.visibility = if (isUserNew) View.INVISIBLE else View.VISIBLE
            tilRepeatPassword.visibility = if (isUserNew) View.INVISIBLE else View.VISIBLE
            btnGetPhoto.visibility = if (isUserNew) View.INVISIBLE else View.VISIBLE
        }
    }

    private fun generateString(): String {
        val symbols = "1234567890qwertyuioplkjhgfdsazxcvbnm"
        val result = StringBuilder()
        for (i in (1..36)) {
            try {
                result.append(symbols[Random().nextInt(symbols.length - 1)])
            } catch (e: Exception) {
                result.append(symbols[2])
            }
        }
        return result.toString()
    }

    private fun isNoEmptyFields(): Boolean {
        return binding?.run {
            tietNickname.text.toString() != "" &&
                    tietDescription.text.toString() != "" &&
                    tietEmail.text.toString() != "" &&
                    tietPassword.text.toString() != "" &&
                    tietRepeatPassword.text.toString() != ""
        } == true
    }

    private fun selectImageFromGallery() {
        val i = Intent(Intent.ACTION_PICK)
        i.type = "image/*"
        startActivityForResult(i, 100)
    }

}
