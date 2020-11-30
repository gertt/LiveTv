package com.gprifti.livetv.ui

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.amitshekhar.DebugDB
import com.gprifti.livetv.R

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        //   navController = findNavController(this, R.id.fragment)
        //  bottomNav.setupWithNavController(navController)

        var sgs = DebugDB.getAddressLog();
        // Open http://192.168.1.2:8080 in your browser

        //setupNav()
    }

//    private fun setupNav() {
//        val navController = findNavController(R.id.fragment)
//        findViewById<BottomNavigationView>(R.id.bottomNav).setupWithNavController(navController)
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            when (destination.id) {
//                R.id.favoriteFragment -> showBottomNav()
//                R.id.popularListFragment -> showBottomNav()
//                R.id.searchFragment -> showBottomNav()
//                else -> hideBottomNav()
//            }
//        }
//    }

//    private fun showBottomNav() {
//        bottomNav.visibility = View.VISIBLE
//    }
//
//    private fun hideBottomNav() {
//        bottomNav.visibility = View.GONE
//    }
}
