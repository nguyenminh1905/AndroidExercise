package com.example.fragmentexercise

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class IncreaseRecycleViewAdapter(private val context: Context) :
    RecyclerView.Adapter<IncreaseRecycleViewAdapter.IncreaseRecycleViewHolder>() {

    private val items = mutableListOf<Item>()


    inner class IncreaseRecycleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val day: TextView = itemView.findViewById(R.id.day)
        val place: TextView = itemView.findViewById(R.id.place)
        val time: TextView = itemView.findViewById(R.id.time)
        val money: TextView = itemView.findViewById(R.id.money)
        val percent: TextView = itemView.findViewById(R.id.percent)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncreaseRecycleViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.increase_item_view, parent, false)
        return IncreaseRecycleViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: IncreaseRecycleViewHolder, position: Int) {
        val item = items[position]
        holder.day.text = "DAY ${item.day}"
        holder.place.text = "LONDON ${item.day}"
        holder.time.text = item.time
        holder.money.text =  item.money.toString()
        holder.percent.text = item.percent
    }


    override fun getItemCount(): Int {
        return items.size
    }


    fun addItems(newItems: List<Item>) {
        items.addAll(newItems)
        notifyItemRangeInserted(items.size - newItems.size, newItems.size)
    }
}