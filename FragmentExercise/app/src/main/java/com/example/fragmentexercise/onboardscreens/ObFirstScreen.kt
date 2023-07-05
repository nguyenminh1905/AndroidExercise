package com.example.fragmentexercise.onboardscreens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.fragmentexercise.R
import com.example.fragmentexercise.databinding.FragmentObFirstScreenBinding


class ObFirstScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ob_first_screen, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val next = view.findViewById<TextView>(R.id.nav_next)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)
        next.setOnClickListener {
            viewPager?.currentItem = 1
        }
    }
}