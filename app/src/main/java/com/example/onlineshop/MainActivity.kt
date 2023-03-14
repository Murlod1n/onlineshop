package com.example.onlineshop

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.onlineshop.viewmodel.OnlineShopViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var bottomNavigation: BottomNavigationView
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)
        bottomNavigation = findViewById(R.id.bottomNavigationView)
        bottomNavigation.setupWithNavController(navController)
        setCustomBottomBarListener()


    }

    private fun setCustomBottomBarListener() {
        bottomNavigation.menu.apply {
            findItem(R.id.productsFragment )
                .setOnMenuItemClickListener {
                    if(!it.isChecked) navController.popBackStack()
                    true
                }

        }
    }
}