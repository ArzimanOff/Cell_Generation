package com.arziman_off.cell_generation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    private val LOG_TAG = "NeedLogs"

    private val mainViewModel: MainViewModel by viewModels()

    private var recyclerView: RecyclerView? = null
    private val adapter: ItemsAdapter by lazy { ItemsAdapter() }
    private var generateButton: MaterialButton? = null
    private var deleteAllCellsBtn: ImageButton? = null
    private var emptyListPlaceholder: LinearLayout? = null
    private var placeholderAnim: LottieAnimationView? = null

    private var tv_live_cells_cnt: TextView? = null
    private var tv_dead_cells_cnt: TextView? = null
    private var tv_new_life_cnt: TextView? = null
    private var tv_dead_life_cnt: TextView? = null



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
                deleteAllCellsBtn?.visibility  = View.GONE
                emptyListPlaceholder?.visibility = View.VISIBLE
                placeholderAnim?.playAnimation()
            } else {
                deleteAllCellsBtn?.visibility = View.VISIBLE
                emptyListPlaceholder?.visibility = View.GONE
                placeholderAnim?.pauseAnimation()
            }
            adapter.submitList(items.toList())
            recyclerView?.scrollToPosition(adapter.itemCount - 1)
        })

        mainViewModel.itemTypesCounter.observe(this, Observer {
            tv_live_cells_cnt?.text = (it[MainViewModel.LIVING_CELL] ?: 0).toString()
            tv_dead_cells_cnt?.text = (it[MainViewModel.DEAD_CELL] ?: 0).toString()
            tv_new_life_cnt?.text = (it[MainViewModel.LIFE] ?: 0).toString()
            tv_dead_life_cnt?.text = (it[MainViewModel.DEAD_LIFE] ?: 0).toString()
        })
    }

    private fun setEventListeners() {
        generateButton?.setOnClickListener {
            mainViewModel.generateAndAddItem()
        }

        deleteAllCellsBtn?.setOnClickListener {
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
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = adapter

        tv_live_cells_cnt = findViewById(R.id.tv_live_cells_cnt)
        tv_dead_cells_cnt = findViewById(R.id.tv_dead_cells_cnt)
        tv_new_life_cnt = findViewById(R.id.tv_new_life_cnt)
        tv_dead_life_cnt = findViewById(R.id.tv_dead_life_cnt)

        generateButton = findViewById(R.id.generateButton)
        deleteAllCellsBtn = findViewById(R.id.deleteAllCellsBtn)

        emptyListPlaceholder = findViewById(R.id.emptyListPlaceholder)

        placeholderAnim = findViewById(R.id.placeholderAnim)
        placeholderAnim?.setMinProgress(0.0f)
        placeholderAnim?.setMaxProgress(1.0f)
        placeholderAnim?.repeatCount = LottieDrawable.INFINITE
        placeholderAnim?.repeatMode = LottieDrawable.RESTART
    }
}