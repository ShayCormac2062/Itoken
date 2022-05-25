package com.example.itoken

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.itoken.databinding.ActivityMainBinding
import com.example.itoken.features.addtoken.presentation.fragment.AddTokenFragment
import com.example.itoken.features.user.presentation.viewmodel.UsersViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private lateinit var controller: NavController

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val usersViewModel: UsersViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        setTheme(R.style.Theme_IToken)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        initNavController()
        checkPermissions()
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED) {
             registerForActivityResult(ActivityResultContracts.RequestPermission()) {}.launch(
                 Manifest.permission.READ_EXTERNAL_STORAGE
             )
        }
    }

    private fun initNavController() {
        controller = (supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment)
            .navController
        binding?.run {
            usersViewModel.getUser()
            bottomMain.setupWithNavController(controller)
        }
    }

}
