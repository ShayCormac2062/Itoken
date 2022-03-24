package com.example.itoken.presentation.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.room.Room
import com.example.itoken.App
import com.example.itoken.R
import com.example.itoken.data.db.AssetsDatabase
import com.example.itoken.data.db.dao.AssetsDao
import com.example.itoken.databinding.ActivityMainBinding
import com.example.itoken.presentation.ui.fragment.AddTokenFragment
import com.example.itoken.presentation.viewmodel.MainViewModel
import com.example.itoken.utils.MainViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    lateinit var controller: NavController
    var database: AssetsDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_IToken)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        database = Room.databaseBuilder(
            applicationContext,
            AssetsDatabase::class.java,
            "dao"
        ).build()
            .assetsDao()
        initNavController()
    }

    private fun initNavController() {
        controller = (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController
        binding?.run {
            bottomMain.setupWithNavController(controller)
            btnAddToken.setOnClickListener {
                supportFragmentManager.beginTransaction()
                    .add(AddTokenFragment(), "FUCK")
                    .commit()
            }
        }
    }

}