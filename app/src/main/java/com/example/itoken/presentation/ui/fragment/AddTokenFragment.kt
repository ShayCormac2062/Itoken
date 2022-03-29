package com.example.itoken.presentation.ui.fragment

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.itoken.R
import com.example.itoken.data.db.dao.AssetsDao
import com.example.itoken.data.db.entity.DatabaseAsset
import com.example.itoken.databinding.FragmentAddTokenBinding
import com.example.itoken.presentation.ui.activity.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddTokenFragment @Inject constructor(
    private val database: AssetsDao?
) : BottomSheetDialogFragment() {

    private var binding: FragmentAddTokenBinding? = null

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
                    )} == PackageManager.PERMISSION_DENIED
                ) {
                    val permissions = arrayOf(CAMERA)
                    requestPermissions(permissions, 100)
                } else {
                    it.isEnabled = true
                    btnGoToGallery.isEnabled = false
                    makePhoto()
                }
            }
            //TODO(потом добавить проверку на категорию)
            btnCreateToken.setOnClickListener {
                if (btnGoToCamera.isActivated || btnGoToGallery.isActivated) {
                    if (tietName.text.toString() != "") {
                        if (tietPrise.text.toString() != "") {
                            createToken()
                        } else Toast.makeText(context, getString(R.string.no_price_notif), Toast.LENGTH_SHORT).show()
                    } else Toast.makeText(context, getString(R.string.no_name_notif), Toast.LENGTH_SHORT).show()
                } else Toast.makeText(context, getString(R.string.no_photo_notif), Toast.LENGTH_SHORT).show()
            }
        }
    }

    //TODO(потом переделать данные токена)
    private fun createToken() {
        binding?.run {
            val newToken = DatabaseAsset(
                (10000000..99999999).random().toString(),
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
                Log.e(newToken.toString(), "FUCK")
                database?.add(newToken)
                Snackbar.make(root, getString(R.string.add_token_susccessful), 4000).show()
                onDestroy()
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

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
