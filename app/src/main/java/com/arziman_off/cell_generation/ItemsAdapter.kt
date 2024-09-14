package com.arziman_off.cell_generation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {
    private var items: List<Int> = listOf()

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.cellTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cell, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val itemType = items[position]
        val text = when (itemType) {
            MainViewModel.DEAD_CELL -> "Dead Cell"
            MainViewModel.LIVING_CELL -> "Living Cell"
            MainViewModel.LIFE -> "Life"
            else -> "Unknown"
        }
        holder.textView.text = text
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(newItems: List<Int>) {
        items = newItems
        notifyDataSetChanged()
    }
}
