package com.example.fragmentexercise

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkFirstStart()
    }


    private fun checkFirstStart() {
        val prefs = getSharedPreferences("FragmentExercise", Context.MODE_PRIVATE)
        val firstStart = prefs.getBoolean("firstStart", true)

        if (firstStart) {
            //update the shared preferences
            val editor = prefs.edit()
            editor.putBoolean("firstStart", false)
            editor.apply()

            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            // Instantiate the navController using the NavHostFragment
            navController = navHostFragment.navController
            navController.navigate(R.id.onBoardingFragment)
        }
    }
}

