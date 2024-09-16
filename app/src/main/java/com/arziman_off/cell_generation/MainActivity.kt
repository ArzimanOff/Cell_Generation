package com.arziman_off.cell_generation

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
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

    private var tvLiveCellsCnt: TextView? = null
    private var tvDeadCellsCnt: TextView? = null
    private var tvNewLifeCnt: TextView? = null
    private var tvDeadLifeCnt: TextView? = null
    private var title: TextView? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews();
        mainViewModel.loadData()
        setEventListeners()
        observeViewModel()
    }

    override fun onPause() {
        super.onPause()
        mainViewModel.saveData()
    }

    private fun observeViewModel() {
        mainViewModel.items.observe(this) { items ->
            if (items.toList().isEmpty()) {
                deleteAllCellsBtn?.visibility = View.GONE
                emptyListPlaceholder?.visibility = View.VISIBLE
                placeholderAnim?.playAnimation()
            } else {
                deleteAllCellsBtn?.visibility = View.VISIBLE
                emptyListPlaceholder?.visibility = View.GONE
                placeholderAnim?.pauseAnimation()
            }
            adapter.submitList(items.toList()) {
                recyclerView?.post {
                    recyclerView?.scrollToPosition(adapter.itemCount - 1)
                }
            }
        }

        mainViewModel.itemTypesCounter.observe(this) {
            tvLiveCellsCnt?.text = (it[MainViewModel.LIVING_CELL] ?: 0).toString()
            tvDeadCellsCnt?.text = (it[MainViewModel.DEAD_CELL] ?: 0).toString()
            tvNewLifeCnt?.text = (it[MainViewModel.LIFE] ?: 0).toString()
            tvDeadLifeCnt?.text = (it[MainViewModel.DEAD_LIFE] ?: 0).toString()
        }
    }

    private fun setEventListeners() {
        generateButton?.setOnClickListener {
            mainViewModel.generateAndAddItem()
        }

        deleteAllCellsBtn?.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.delete_all_cells_dialog_title_text))
                .setMessage(getString(R.string.delete_all_cells_dialog_msg_text))
                .setPositiveButton(getString(R.string.delete_all_cells_dialog_positive_btn_text)) { dialog, which ->
                    mainViewModel.deleteAll()
                }
                .setNegativeButton(getString(R.string.delete_all_cells_dialog_negative_btn_text)) { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        }
        title?.setOnClickListener {
            recyclerView?.smoothScrollToPosition(0)
        }

        adapter.setOnItemClickListener(object : ItemsAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                recyclerView?.smoothScrollToPosition(position)
            }
        })

    }


    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = adapter

        tvLiveCellsCnt = findViewById(R.id.tv_live_cells_cnt)
        tvDeadCellsCnt = findViewById(R.id.tv_dead_cells_cnt)
        tvNewLifeCnt = findViewById(R.id.tv_new_life_cnt)
        tvDeadLifeCnt = findViewById(R.id.tv_dead_life_cnt)
        title = findViewById(R.id.title)

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