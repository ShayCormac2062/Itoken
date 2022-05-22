package com.example.itoken.features.addtoken.presentation.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.itoken.App
import com.example.itoken.R
import com.example.itoken.common.viewmodel.CurrentUserViewModel
import com.example.itoken.databinding.FragmentAddTokenBinding
import com.example.itoken.databinding.FragmentCanvasBinding
import com.example.itoken.features.addtoken.domain.model.AssetModel
import com.example.itoken.features.addtoken.presentation.viewmodel.AddTokenViewModel
import com.example.itoken.utils.CommonUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddTokenFragment : BottomSheetDialogFragment() {

    private var binding: FragmentAddTokenBinding? = null
    private var url: String? = null
    private var name: String? = null

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
                selectImageFromGallery()
            }
            btnGoToCanvas.setOnClickListener {
                paintToken()
            }
            btnCreateToken.setOnClickListener {
                with(CommonUtils) {
                    if (btnGoToCanvas.isActivated || btnGoToGallery.isActivated) {
                        if (tietName.text.toString() != "") {
                            if (tietPrise.text.toString() != "") {
                                showDialog(
                                    context,
                                    getString(R.string.ending_create_token),
                                    onClickEvent = {
                                        createToken()
                                    })
                            } else makeToast(context, getString(R.string.no_price))
                        } else makeToast(context, getString(R.string.no_name))
                    } else makeToast(context, getString(R.string.no_photo))
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
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

    private fun initObservers() {
        usersViewModel.currentUser.observe(viewLifecycleOwner) { user ->
            name = user?.nickname
        }
    }

    private fun paintToken() {
        val bindingOfDialog: FragmentCanvasBinding
        val alerts = AlertDialog.Builder(context).apply {
            bindingOfDialog = FragmentCanvasBinding.inflate(LayoutInflater.from(context))
            setView(bindingOfDialog.root)
        }.show()
        bindingOfDialog.canvas.init(activity?.contentResolver, alerts)
    }

    private fun createToken() {
        try {
            binding?.run {
                val newToken = AssetModel(
                    "0",
                    url,
                    url,
                    name,
                    "",
                    tietName.text.toString(),
                    tietPrise.text.toString().toLong(),
                    0,
                    tietDescription.text.toString(),
                    "0x${(10000000..99999999).random()}"
                )
                viewModel.add(newToken)
                CommonUtils.makeToast(context, getString(R.string.add_token_successful))
                dismiss()
            }
        } catch (e: Exception) {
            CommonUtils.makeToast(context, getString(R.string.too_expensive_token))
        }
    }

    private fun selectImageFromGallery() {
        val i = Intent(Intent.ACTION_PICK)
        i.type = "image/*"
        startActivityForResult(i, 100)
    }

}

