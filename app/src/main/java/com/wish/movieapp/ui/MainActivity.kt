package com.wish.movieapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.wish.movieapp.R
import com.wish.movieapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _activityMainBinding: ActivityMainBinding? = null
    private val binding get() = _activityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        bottomNavigation()
    }

    private fun bottomNavigation() {
        val bottomNavigationView = binding?.bottomNavMain
        val navigationHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_main) as NavHostFragment

        if (bottomNavigationView != null) {
            NavigationUI.setupWithNavController(bottomNavigationView, navigationHostFragment.navController)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }

    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }
}