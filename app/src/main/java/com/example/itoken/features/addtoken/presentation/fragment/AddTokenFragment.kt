package com.example.itoken.features.addtoken.presentation.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.example.itoken.features.addtoken.presentation.viewmodel.CurrentUserViewModel
import com.example.itoken.features.user.domain.model.UserModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import javax.inject.Inject


class AddTokenFragment : BottomSheetDialogFragment() {

    private var binding: FragmentAddTokenBinding? = null
    private lateinit var url: String
    private var currentUser: UserModel? = null

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: AddTokenViewModel by viewModels {
        factory
    }
    private val usersViewModel: CurrentUserViewModel by viewModels {
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
            initObservers()
            lifecycleScope.launch {
                usersViewModel.getUser()
            }
            btnGoToGallery.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    selectImageFromGallery()
                }
            }
            btnGoToCanvas.setOnClickListener {
                paintToken()
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

    private fun initObservers() {
        usersViewModel.currentUser.observe(viewLifecycleOwner) { user ->
            currentUser = user
        }
    }

    private fun paintToken() {
        CanvasFragment().show(childFragmentManager, "DAMN")
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
        currentUser = null
    }

    //TODO(потом переделать данные токена)
    private fun createToken() {
        try {
            binding?.run {
                val newToken = AssetModel(
                    "0",
                    url,
                    url,
                    currentUser?.nickname,
                    "",
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
        } catch (e: Exception) {
            makeToast("Ваш токен слишком дорогой, занизьте цену")
        }
    }

    private fun selectImageFromGallery() {
        val i = Intent(Intent.ACTION_PICK)
        i.type = "image/*"
        startActivityForResult(i, 100)
    }
}

