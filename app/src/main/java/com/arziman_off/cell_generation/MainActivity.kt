package com.arziman_off.cell_generation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    private val mainViewModel : MainViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemsAdapter
    private lateinit var generateButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews();
        setEventListeners()
        observeViewModel()
    }

    private fun observeViewModel() {
        mainViewModel.items.observe(this, Observer { items ->
            adapter.submitList(items.toList())
            recyclerView.scrollToPosition(adapter.itemCount - 1)
        })
    }

    private fun setEventListeners() {
        generateButton.setOnClickListener {
            mainViewModel.generateAndAddItem()
        }
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ItemsAdapter()
        recyclerView.adapter = adapter

        generateButton = findViewById(R.id.generateButton)
    }
}