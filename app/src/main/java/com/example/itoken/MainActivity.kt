package com.example.itoken

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.itoken.databinding.ActivityMainBinding
import com.example.itoken.features.addtoken.presentation.fragment.AddTokenFragment

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private lateinit var controller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_IToken)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        initNavController()
    }

    private fun initNavController() {
        controller = (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController
        binding?.run {
            bottomMain.setupWithNavController(controller)
            btnAddToken.setOnClickListener {
                supportFragmentManager.beginTransaction()
                    .add(AddTokenFragment(), "FUCK")
//                    .addToBackStack(null)
                    .commit()
            }
        }
    }

}
