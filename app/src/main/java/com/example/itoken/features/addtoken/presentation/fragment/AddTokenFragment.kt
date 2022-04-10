package com.example.itoken.features.addtoken.presentation.fragment

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.itoken.App
import com.example.itoken.R
import com.example.itoken.databinding.FragmentAddTokenBinding
import com.example.itoken.databinding.ViewNotificationBinding
import com.example.itoken.features.addtoken.domain.model.AssetModel
import com.example.itoken.features.addtoken.presentation.viewmodel.AddTokenViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import javax.inject.Inject


class AddTokenFragment : BottomSheetDialogFragment() {

    private var binding: FragmentAddTokenBinding? = null
    private lateinit var url: String

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: AddTokenViewModel by viewModels {
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
        binding = FragmentAddTokenBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.run {
            btnGoToGallery.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkPermission(READ_EXTERNAL_STORAGE)) {
                        selectImageFromGallery()
                    }
                }
            }
            btnGoToCanvas.setOnClickListener {
                if (checkPermission(WRITE_EXTERNAL_STORAGE)) {
                    paintToken()
                }
            }
            //TODO(потом добавить проверку на категорию)
            btnCreateToken.setOnClickListener {
                if (btnGoToCanvas.isActivated || btnGoToGallery.isActivated) {
                    if (tietName.text.toString() != "") {
                        if (tietPrise.text.toString() != "") {
                            showNotification()
                        } else makeToast(getString(R.string.no_price_notif))
                    } else makeToast(getString(R.string.no_name_notif))
                } else makeToast(getString(R.string.no_photo_notif))
            }
        }
    }

    private fun paintToken() {
        CanvasFragment().show(childFragmentManager, "DAMN")
    }

    private fun checkPermission(permission: String): Boolean {
        return if (context?.let { it1 ->
                checkSelfPermission(
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

    private fun makeToast(text: String) = Toast.makeText(
        context,
        text,
        Toast.LENGTH_SHORT
    ).show()

    private fun showNotification() {
        val bindingOfDialog: ViewNotificationBinding
        val alerts = AlertDialog.Builder(context).apply {
            bindingOfDialog = ViewNotificationBinding.inflate(LayoutInflater.from(context))
            setView(bindingOfDialog.root)
        }.show()
        with(bindingOfDialog) {
            btnNo.setOnClickListener {
                alerts.dismiss()
            }
            btnYes.setOnClickListener {
                createToken()
                alerts.dismiss()
            }
            tvNotification.text = context?.getString(R.string.notif_ending_create_token)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            when (permissions[0]) {
                READ_EXTERNAL_STORAGE -> selectImageFromGallery()
                else -> paintToken()
            }
        } else {
            Toast.makeText(
                context,
                when (permissions[0]) {
                    READ_EXTERNAL_STORAGE -> "Доступ к фотографиям запрещён"
                    else -> "Доступ к камере запрещён"
                },
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 100) {
                binding?.run {
                    ivForPicture.load(data?.data)
                    url = data?.data.toString()
                    btnGoToGallery.isActivated = true
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    //TODO(потом переделать данные токена)
    private fun createToken() {
        binding?.run {
            val newToken = AssetModel(
                "0",
                url,
                url,
                "FUCKYou228",
                "FUCKYou228",
                tietName.text.toString(),
                tietPrise.text.toString().toInt(),
                0,
                tietDescription.text.toString(),
                "0x${(10000000..99999999).random()}"
            )
            lifecycleScope.launch {
                viewModel.add(newToken)
                makeToast(getString(R.string.add_token_susccessful))
                onDestroyView()
            }
        }
    }

    private fun selectImageFromGallery() {
        val i = Intent(Intent.ACTION_PICK)
        i.type = "image/*"
        startActivityForResult(i, 100)
    }
}

