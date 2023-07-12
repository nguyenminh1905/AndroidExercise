package com.example.fragmentexercise.appscreens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fragmentexercise.IncreaseRecycleViewAdapter
import com.example.fragmentexercise.R
import com.example.fragmentexercise.TabRecycleViewAdapter
import com.example.fragmentexercise.Item

class AppFirstScreen : Fragment() {

    private lateinit var increaseAdapter: IncreaseRecycleViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_app_first_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val items = listOf("INDEX", "SHARES", "CURRENCIES", "FUTURES")
        val tabAdapter = TabRecycleViewAdapter(items)

        val tabRecyclerView = view.findViewById<RecyclerView>(R.id.tabRecyclerView)
        tabRecyclerView.adapter = tabAdapter

        val increaseRecycleView = view.findViewById<RecyclerView>(R.id.increaseRecyclerView)
        increaseAdapter = IncreaseRecycleViewAdapter(requireContext())
        increaseRecycleView.adapter = increaseAdapter

        loadItems() // Load initial items

        val loadMoreButton = view.findViewById<Button>(R.id.bt_load)
        loadMoreButton.setOnClickListener {
            loadItems() // Load more items when button is clicked
        }
    }

    private fun loadItems() {
        val newItems = mutableListOf<Item>()
        val start = increaseAdapter.itemCount
        for (i in start until start + 10) {
            newItems.add(Item(i, "LONDON $i", "10:44:$i", 20.047 + i, "+203(+1,$i%)"))
        }
        increaseAdapter.addItems(newItems)
    }
}