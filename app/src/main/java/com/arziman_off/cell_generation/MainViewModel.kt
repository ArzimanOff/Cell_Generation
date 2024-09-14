package com.arziman_off.cell_generation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){
    companion object {
        const val SEQUENCE_FOR_LIFE = 3

        const val DEAD_CELL = 0
        const val LIVING_CELL = 1
        const val LIFE = 2
    }
    val items: MutableLiveData<MutableList<Int>> = MutableLiveData(mutableListOf())
    fun generateAndAddItem() {

    }
}