package com.example.itoken.features.user.presentation.fragment.authentication

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.itoken.App
import com.example.itoken.R
import com.example.itoken.databinding.FragmentRegistrationBinding
import com.example.itoken.features.user.domain.model.ItemAsset
import com.example.itoken.features.user.domain.model.UserModel
import com.example.itoken.features.user.presentation.viewmodel.UsersViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
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
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run {
            btnRegister.setOnClickListener {
                if (isNoEmptyFields()) {
                    registerUser()
                } else makeToast("Заполните все поля")
            }
            btnGetPhoto.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        selectImageFromGallery()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun registerUser() {
        binding?.run {
            btnRegister.setOnClickListener {
                if (tietPassword.text.toString() == tietRepeatPassword.text.toString()) {
                    lifecycleScope.launch {
                        if (usersViewModel.registerUser(
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
                            )) {
                            findNavController().navigate(R.id.loadingFragment)
                        } else makeToast("Такой пользователь уже существует")
                    }
                } else makeToast("Пароли должны совпадать")
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            when (permissions[0]) {
                Manifest.permission.READ_EXTERNAL_STORAGE -> selectImageFromGallery()
                else -> null
            }
        } else {
            Toast.makeText(
                context,
                when (permissions[0]) {
                    Manifest.permission.READ_EXTERNAL_STORAGE -> "Доступ к фотографиям запрещён"
                    else -> ""
                },
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 100) {
                imageUrl = data?.data.toString()
                binding?.btnGetPhoto?.isActivated = true
            }
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
                    tietRepeatPassword.text.toString() != "" &&
                    btnGetPhoto.isActivated
        } == true
    }

    private fun checkPermission(permission: String): Boolean {
        return if (context?.let { it1 ->
                ContextCompat.checkSelfPermission(
                    it1,
                    permission
                )
            } == PackageManager.PERMISSION_DENIED
        ) {
            val permissions = arrayOf(permission)
            requestPermissions(permissions, 100)
            false
        } else true
    }

    private fun selectImageFromGallery() {
        val i = Intent(Intent.ACTION_PICK)
        i.type = "image/*"
        startActivityForResult(i, 100)
    }

    private fun makeToast(message: String) = Toast.makeText(
        context,
        message,
        Toast.LENGTH_SHORT
    ).show()

}
