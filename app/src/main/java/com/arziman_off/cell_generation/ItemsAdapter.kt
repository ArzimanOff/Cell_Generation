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
    private var listener: OnItemClickListener? = null


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cellTextView: TextView = itemView.findViewById(R.id.cellTextView)
        private val cellIdText: TextView = itemView.findViewById(R.id.cellIdText)
        private val cellTextPostscript: TextView = itemView.findViewById(R.id.cellTextPostscript)
        private val cellIconView: ImageView = itemView.findViewById(R.id.cellIconView)
        fun bind(cell: Cell) {
            val text: String = when (cell.type) {
                MainViewModel.DEAD_CELL -> itemView.context.getString(R.string.dead_cell_title_text)
                MainViewModel.SPEC_DEAD_CELL -> itemView.context.getString(R.string.dead_cell_title_text)

                MainViewModel.LIVING_CELL -> itemView.context.getString(R.string.live_cell_title_text)
                MainViewModel.SPEC_LIVING_CELL -> itemView.context.getString(R.string.live_cell_title_text)

                MainViewModel.LIFE -> itemView.context.getString(R.string.life_title_text)
                MainViewModel.DEAD_LIFE -> itemView.context.getString(R.string.dead_life_title_text)
                else -> "Unknown"
            }
            val textPostscript: String = when (cell.type) {
                MainViewModel.DEAD_CELL -> itemView.context.getString(R.string.dead_cell_postcript_text)
                MainViewModel.SPEC_DEAD_CELL -> itemView.context.getString(R.string.dead_cell_postcript_text)

                MainViewModel.LIVING_CELL -> itemView.context.getString(R.string.live_cell_postcript_text)
                MainViewModel.SPEC_LIVING_CELL -> itemView.context.getString(R.string.live_cell_postcript_text)

                MainViewModel.LIFE -> itemView.context.getString(R.string.life_cell_postcript_text)
                MainViewModel.DEAD_LIFE -> itemView.context.getString(R.string.dead_life_cell_postcript_text)
                else -> "Unknown"
            }
            val drawable: Comparable<*> = when (cell.type) {
                MainViewModel.DEAD_CELL -> R.drawable.ic_dead_cell
                MainViewModel.SPEC_DEAD_CELL -> R.drawable.ic_dead_cell

                MainViewModel.LIVING_CELL -> R.drawable.ic_live_cell
                MainViewModel.SPEC_LIVING_CELL -> R.drawable.ic_live_cell

                MainViewModel.LIFE -> R.drawable.ic_new_life
                MainViewModel.DEAD_LIFE -> R.drawable.ic_dead_life_cell
                else -> R.drawable.ic_launcher_foreground
            }
            val itemBg: Comparable<*> = when (cell.type) {
                MainViewModel.DEAD_CELL -> R.drawable.item_bg
                MainViewModel.SPEC_DEAD_CELL -> R.drawable.item_spec_dead_cell_bg

                MainViewModel.LIVING_CELL -> R.drawable.item_bg
                MainViewModel.SPEC_LIVING_CELL -> R.drawable.item_life_bg

                MainViewModel.DEAD_LIFE -> R.drawable.item_dead_life_bg
                MainViewModel.LIFE -> R.drawable.item_life_bg
                else -> R.drawable.item_bg
            }

            if (cell.type == MainViewModel.LIFE){
                val fadeInAnimation = AnimationUtils.loadAnimation(itemView.context, R.anim.fade_in)
                itemView.animation = fadeInAnimation
            }

            cellIdText.text = String.format("#${cell.id + 1}")
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
        if (cell.type == MainViewModel.SPEC_DEAD_CELL && cell.deadLifePosition != -1){
            holder.itemView.rootView.setOnClickListener {
                listener?.onClick(cell.deadLifePosition)
            }
        }
        holder.bind(cell)
    }


    class DiffCallback : DiffUtil.ItemCallback<Cell>() {
        override fun areItemsTheSame(oldItem: Cell, newItem: Cell): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cell, newItem: Cell): Boolean {
            return oldItem == newItem
        }
    }

    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }


}
