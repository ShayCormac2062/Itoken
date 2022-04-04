package com.example.itoken.features.addtoken.presentation.fragment

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
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
                    if (context?.let { it1 ->
                            checkSelfPermission(
                                it1,
                                READ_EXTERNAL_STORAGE
                            )
                        } == PackageManager.PERMISSION_DENIED) {
                        val permissions = arrayOf(READ_EXTERNAL_STORAGE)
                        requestPermissions(permissions, 100)
                    } else {
                        selectImageFromGallery()
                    }
                }
            }
            btnGoToCamera.setOnClickListener {
                if (context?.let { it1 ->
                        checkSelfPermission(
                            it1,
                            CAMERA
                        )
                    } == PackageManager.PERMISSION_DENIED
                ) {
                    val permissions = arrayOf(CAMERA)
                    requestPermissions(permissions, 100)
                } else {
                    makePhoto()
                }
            }
            //TODO(потом добавить проверку на категорию)
            btnCreateToken.setOnClickListener {
                if (btnGoToCamera.isActivated || btnGoToGallery.isActivated) {
                    if (tietName.text.toString() != "") {
                        if (tietPrise.text.toString() != "") {
                            showNotification()
                        } else Toast.makeText(
                            context,
                            getString(R.string.no_price_notif),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else Toast.makeText(
                        context,
                        getString(R.string.no_name_notif),
                        Toast.LENGTH_SHORT
                    ).show()
                } else Toast.makeText(
                    context,
                    getString(R.string.no_photo_notif),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showNotification() {
        val bindingOfDialog: ViewNotificationBinding
        val alerts =  AlertDialog.Builder(context).apply {
            bindingOfDialog = ViewNotificationBinding.inflate(LayoutInflater.from(context))
            setView(bindingOfDialog.root)
        }.show()
        with (bindingOfDialog) {
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
                CAMERA -> makePhoto()
                else -> selectImageFromGallery()
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
                val thumbNail = data?.extras?.get("data") as Bitmap
                binding?.run {
                    ivForPicture.load(thumbNail)
                    btnGoToGallery.isActivated = true
                    btnGoToCamera.isActivated = false
                }
            } else {
                binding?.run {
                    ivForPicture.load(data?.data)
                    btnGoToGallery.isActivated = true
                    btnGoToCamera.isActivated = false
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
                0,
                ivForPicture.drawable.toString(),
                ivForPicture.drawable.toString(),
                "FUCKYou228",
                "FUCKYou228",
                tietName.text.toString(),
                tietPrise.text.toString().toInt(), 0,
                tietDescription.text.toString(),
                "0x${(10000000..99999999).random()}"
            )
            lifecycleScope.launch {
                viewModel.add(newToken)
                Toast.makeText(context, getString(R.string.add_token_susccessful), Toast.LENGTH_LONG)
                    .show()
                onDestroyView()
            }
        }
    }

    private fun makePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 100)
    }

    private fun selectImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 200)
    }
}
