package com.example.fragmentexercise

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class TabRecycleViewAdapter(private val items: List<String>) :
    RecyclerView.Adapter<TabRecycleViewAdapter.TabRecycleViewHolder>() {
    private var selectedItem = -1

    inner class TabRecycleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabRecycleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return TabRecycleViewHolder(view)
    }

    override fun onBindViewHolder(holder: TabRecycleViewHolder, position: Int) {
        holder.textView.text = items[position]

        holder.itemView.setOnClickListener {
            val previousItem = selectedItem
            selectedItem = holder.adapterPosition
            notifyItemChanged(previousItem)
            notifyItemChanged(selectedItem)
        }
        holder.itemView.isSelected = position == selectedItem
    }


    override fun getItemCount(): Int {
        return items.size
    }
}

