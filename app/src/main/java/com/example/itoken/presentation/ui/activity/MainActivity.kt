package com.example.itoken.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.itoken.R
import com.example.itoken.data.retrofit.di.DIContainer
import com.example.itoken.databinding.ActivityMainBinding
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private lateinit var controller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_IToken)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        initNavController()
        binding?.btnAddToken?.setOnClickListener {
            //TODO BottomSheet
        }
    }

    private fun initNavController() {
        controller = (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController
        binding?.bottomMain?.setupWithNavController(controller)
    }

}