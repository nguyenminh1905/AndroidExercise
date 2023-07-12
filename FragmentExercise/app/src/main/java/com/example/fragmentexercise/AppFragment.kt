package com.example.fragmentexercise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.fragmentexercise.appscreens.AppFirstScreen
import com.example.fragmentexercise.appscreens.AppFourthScreen
import com.example.fragmentexercise.appscreens.AppSecondScreen
import com.example.fragmentexercise.appscreens.AppThirdScreen
import com.example.fragmentexercise.onboardscreens.ObFirstScreen
import com.example.fragmentexercise.onboardscreens.ObSecondScreen
import com.example.fragmentexercise.onboardscreens.ObThirdScreen
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AlertDialog

class AppFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_app, container, false)

        val fragmentList = listOf(
            AppFirstScreen(),
            AppSecondScreen(),
            AppThirdScreen(),
            AppFourthScreen()
        )

        val adapter =
            ViewPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)

        val viewPager = view.findViewById<ViewPager2>(R.id.view_pager)

        viewPager.adapter = adapter
        val bottomNav = view.findViewById<BottomNavigationView>(R.id.bottom_nav)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bottomNav.menu.getItem(position).isChecked = true
            }
        })

        // Set the BottomNavigationView item selected listener
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.increase -> viewPager.currentItem = 0
                R.id.pie -> viewPager.currentItem = 1
                R.id.calendar -> viewPager.currentItem = 2
                R.id.profile -> viewPager.currentItem = 3
            }
            true
        }

        val callback = object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                // Handle the back button event
                if (viewPager.currentItem != 0) {
                    viewPager.currentItem -= 1
                } else {
                    showExitDialog()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        return view
    }

    private fun showExitDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage("Do you want to exit the app?")
            .setCancelable(false)
            .setPositiveButton("Yes") { _, _ -> requireActivity().finish() }
            .setNegativeButton("No", null)
            .show()
    }
}