package com.arziman_off.cell_generation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Random

class MainViewModel : ViewModel() {
    private val LOG_TAG = "NeedLogs"
    companion object {
        const val SEQUENCE_FOR_LIFE = 3
        const val SEQUENCE_FOR_LIFE_DELETE = 3

        const val DEAD_CELL = 0
        const val LIVING_CELL = 1
        const val LIFE = 2
    }

    private val random = Random()
    val items: MutableLiveData<MutableList<Int>> = MutableLiveData(mutableListOf())


    fun deleteAll() {
        items.postValue(mutableListOf())
    }

    fun generateAndAddItem() {
        val newItem = if (random.nextBoolean()) DEAD_CELL else LIVING_CELL
        items.value?.add(newItem)
        items.postValue(items.value)

        checkNewLife()
        checkLifeDying()

        items.postValue(items.value)
        Log.d(LOG_TAG, items.value.toString())
    }
    private fun checkNewLife() {
        if (items.value?.size!! >= SEQUENCE_FOR_LIFE &&
            items.value?.takeLast(SEQUENCE_FOR_LIFE)?.all { it == 1 } == true) {

            // Добавляем LIFE после трех LIVING_CELL
            items.value?.add(LIFE)
        }
    }
    private fun checkLifeDying() {
        if (items.value?.takeLast(SEQUENCE_FOR_LIFE_DELETE)?.all { it == 0 } == true) {

            // Удаляем последнюю LIFE после трех DEAD_CELL
            val lastIndex = items.value?.lastIndexOf(LIFE)
            if (lastIndex != null && lastIndex != -1) {
                items.value?.removeAt(lastIndex)
            }
        }
    }
}
