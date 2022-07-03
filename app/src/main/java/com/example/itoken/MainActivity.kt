package com.example.itoken

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.itoken.common.viewmodel.CurrentUserViewModel
import com.example.itoken.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private lateinit var controller: NavController

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
            bottomMain.setupWithNavController(controller)
        }
    }

    override fun onBackPressed() {
        //do nothing

    }

}
