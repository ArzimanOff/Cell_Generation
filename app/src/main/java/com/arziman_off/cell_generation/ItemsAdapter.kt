package com.arziman_off.cell_generation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {
    private var items: List<Int> = listOf()

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cellTextView: TextView = itemView.findViewById(R.id.cellTextView)
        val cellTextPostscript: TextView = itemView.findViewById(R.id.cellTextPostscript)
        val cellIconView: ImageView = itemView.findViewById(R.id.cellIconView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cell, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val itemType = items[position]

        val itemBg : Comparable<*> = when (itemType) {
            MainViewModel.DEAD_CELL -> R.drawable.item_bg
            MainViewModel.LIVING_CELL -> R.drawable.item_bg
            MainViewModel.LIFE -> R.drawable.item_life_bg
            else -> R.drawable.item_bg
        }
        val text = when (itemType) {
            MainViewModel.DEAD_CELL -> "Мертвая клетка"
            MainViewModel.LIVING_CELL -> "Живая клетка"
            MainViewModel.LIFE -> "Жизнь!"
            else -> "Unknown"
        }
        val textPostscript = when (itemType) {
            MainViewModel.DEAD_CELL -> "или прикидывается"
            MainViewModel.LIVING_CELL -> "и шевелится!"
            MainViewModel.LIFE -> "Ку-ку!"
            else -> "Unknown"
        }
        val drawable : Comparable<*> = when (itemType) {
            MainViewModel.DEAD_CELL -> R.drawable.ic_dead_cell
            MainViewModel.LIVING_CELL -> R.drawable.ic_live_cell
            MainViewModel.LIFE -> R.drawable.ic_new_life
            else -> R.drawable.ic_launcher_foreground
        }

        holder.itemView.rootView.setBackgroundResource(itemBg as Int)
        holder.cellTextView.text = text
        holder.cellTextPostscript.text = textPostscript
        holder.cellIconView.setImageResource(drawable as Int)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(newItems: List<Int>) {
        items = newItems
        notifyDataSetChanged()
    }
}
