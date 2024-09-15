package com.arziman_off.cell_generation

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemsAdapter
    private lateinit var generateButton: MaterialButton
    private lateinit var deleteAllCellsBtn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews();
        setEventListeners()
        observeViewModel()
    }

    private fun observeViewModel() {
        mainViewModel.items.observe(this, Observer { items ->
            if (items.toList().isEmpty()) {
                deleteAllCellsBtn.visibility = View.GONE
            } else {
                deleteAllCellsBtn.visibility = View.VISIBLE
            }
            adapter.submitList(items.toList())
            recyclerView.scrollToPosition(adapter.itemCount - 1)
        })
    }

    private fun setEventListeners() {
        generateButton.setOnClickListener {
            mainViewModel.generateAndAddItem()
        }

        deleteAllCellsBtn.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Удалить все сгенерированные клетки?")
                .setMessage("Очистится весь список, вернуть не получится")
                .setPositiveButton("Да, удалить") { dialog, which ->
                    mainViewModel.deleteAll()
                }
                .setNegativeButton("Нет, отмена") { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        }
    }


    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ItemsAdapter()
        recyclerView.adapter = adapter

        generateButton = findViewById(R.id.generateButton)
        deleteAllCellsBtn = findViewById(R.id.deleteAllCellsBtn)
    }
}