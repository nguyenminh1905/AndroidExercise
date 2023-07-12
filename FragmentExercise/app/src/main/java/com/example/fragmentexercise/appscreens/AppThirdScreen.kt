package com.example.fragmentexercise.appscreens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.fragmentexercise.R
import com.example.fragmentexercise.TabRecycleViewAdapter

class AppThirdScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_app_third_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val items = listOf("EDITORIAL", "CRYPTO NEWS", "RAW MATERIAL", "ECONOMICS")
        val adapter = TabRecycleViewAdapter(items)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter

        //val fragmentTransaction = parentFragmentManager.beginTransaction()
        val atlanticLayout = view.findViewById<RelativeLayout>(R.id.atlantia)
        //atlanticLayout.setOnClickListener{
        //    fragmentTransaction.replace(R.id.frame_layout, AtlantaScreen())
        //    fragmentTransaction.addToBackStack(null)
        //    fragmentTransaction.commit()
        //}

        atlanticLayout.setOnClickListener{
            findNavController().navigate(R.id.action_appFragment_to_atlantaScreen)
        }

        val xiaomiLayout = view.findViewById<RelativeLayout>(R.id.xiaomi)

        xiaomiLayout.setOnClickListener{
            findNavController().navigate(R.id.action_appFragment_to_xiaomiScreen)
        }

        val appleLayout = view.findViewById<RelativeLayout>(R.id.apple)

        appleLayout.setOnClickListener {
            findNavController().navigate(R.id.action_appFragment_to_appleScreen)
        }
    }
}