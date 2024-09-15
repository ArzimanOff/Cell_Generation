package com.arziman_off.cell_generation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ItemsAdapter : ListAdapter<Cell, ItemsAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cellTextView: TextView = itemView.findViewById(R.id.cellTextView)
        val cellTextPostscript: TextView = itemView.findViewById(R.id.cellTextPostscript)
        val cellIconView: ImageView = itemView.findViewById(R.id.cellIconView)
        fun bind(cell: Cell) {
            val text: String = when (cell.type) {
                MainViewModel.DEAD_CELL -> "Мертвая клетка"
                MainViewModel.LIVING_CELL -> "Живая клетка"
                MainViewModel.LIFE -> "Жизнь!"
                MainViewModel.DEAD_LIFE -> "Погибшая жизнь"
                else -> "Unknown"
            }
            val textPostscript: String = when (cell.type) {
                MainViewModel.DEAD_CELL -> "или прикидывается"
                MainViewModel.LIVING_CELL -> "и шевелится!"
                MainViewModel.LIFE -> "Ку-ку!"
                MainViewModel.DEAD_LIFE -> "Уже не ку-ку :("
                else -> "Unknown"
            }
            val drawable: Comparable<*> = when (cell.type) {
                MainViewModel.DEAD_CELL -> R.drawable.ic_dead_cell
                MainViewModel.LIVING_CELL -> R.drawable.ic_live_cell
                MainViewModel.LIFE -> R.drawable.ic_new_life
                MainViewModel.DEAD_LIFE -> R.drawable.ic_dead_life_cell
                else -> R.drawable.ic_launcher_foreground
            }
            val itemBg: Comparable<*> = when (cell.type) {
                MainViewModel.DEAD_CELL -> R.drawable.item_bg
                MainViewModel.LIVING_CELL -> R.drawable.item_bg
                MainViewModel.LIFE -> R.drawable.item_life_bg
                MainViewModel.DEAD_LIFE -> R.drawable.item_dead_life_bg
                else -> R.drawable.item_bg
            }

            if (cell.type == MainViewModel.LIFE){
                val fadeInAnimation = AnimationUtils.loadAnimation(itemView.context, R.anim.fade_in)
                itemView.animation = fadeInAnimation
            }

            cellTextView.text = text
            cellTextPostscript.text = textPostscript
            cellIconView.setImageResource(drawable as Int)
            itemView.rootView.setBackgroundResource(itemBg as Int)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cell, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cell = getItem(position)
        holder.bind(cell)
    }


    class DiffCallback : DiffUtil.ItemCallback<Cell>() {
        override fun areItemsTheSame(oldItem: Cell, newItem: Cell): Boolean {
            // Сравнивайте элементы по их уникальным id
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cell, newItem: Cell): Boolean {
            // Сравнивайте элементы по содержимому
            return oldItem == newItem
        }
    }
}
