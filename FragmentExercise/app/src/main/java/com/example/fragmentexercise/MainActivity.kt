package com.example.fragmentexercise

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.fragmentexercise.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prefs = getSharedPreferences("FragmentExercise", Context.MODE_PRIVATE)
        val firstStart = prefs.getBoolean("firstStart", true)

        if (firstStart){
            val editor = prefs.edit()
            editor.putBoolean("firstStart", false)
            editor.apply()

            val navController = findNavController(R.id.nav_host_fragment)
            navController.navigate(R.id.onBoardingFragment)
        }
    }
}
