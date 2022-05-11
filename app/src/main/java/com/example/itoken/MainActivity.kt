package com.example.itoken

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.itoken.databinding.ActivityMainBinding
import com.example.itoken.features.addtoken.presentation.fragment.AddTokenFragment
import com.example.itoken.features.user.presentation.viewmodel.UsersViewModel
import kotlinx.coroutines.launch
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
    }

    private fun initNavController() {
        initObservers()
        controller = (supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment)
            .navController
        binding?.run {
            lifecycleScope.launch {
                usersViewModel.getUser()
            }
            bottomMain.setupWithNavController(controller)
            btnAddToken.setOnClickListener {
                supportFragmentManager.beginTransaction()
                    .add(AddTokenFragment(), "FUCK")
                    .commit()
            }
        }
    }

    private fun initObservers() {
        usersViewModel.currentUser.observe(this) { t ->
            if (t == null) changeButtonVisibility(true)
        }
    }

    fun changeButtonVisibility(b: Boolean) {
        binding?.btnAddToken?.visibility = if (b) View.GONE else View.VISIBLE
    }

}
