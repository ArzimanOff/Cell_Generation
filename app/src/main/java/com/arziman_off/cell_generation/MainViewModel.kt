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
        const val DEAD_LIFE = 3
    }

    private val random = Random()
    val items: MutableLiveData<MutableList<Int>> = MutableLiveData(mutableListOf())
    val itemTypesCounter: MutableLiveData<MutableMap<Int, Int>> = MutableLiveData(mutableMapOf())


    fun deleteAll() {
        items.postValue(mutableListOf())
        itemTypesCounter.postValue(mutableMapOf())
    }

    fun generateAndAddItem() {
        val newItem = if (random.nextBoolean()) DEAD_CELL else LIVING_CELL
        items.value?.add(newItem)
        increaseMapItem(newItem)
        items.postValue(items.value)

        checkNewLife()
        checkLifeDying()

        items.postValue(items.value)
        Log.d(LOG_TAG, items.value.toString())
    }

    private fun checkNewLife() {
        if (items.value?.size!! >= SEQUENCE_FOR_LIFE &&
            items.value?.takeLast(SEQUENCE_FOR_LIFE)?.all
            { it == LIVING_CELL } == true
        ) {

            // Добавляем LIFE после трех LIVING_CELL
            items.value?.add(LIFE)
            increaseMapItem(LIFE)
        }
    }

    private fun checkLifeDying() {
        if (items.value?.takeLast(SEQUENCE_FOR_LIFE_DELETE + 1)?.let { sublist ->
                    sublist.first() != DEAD_CELL &&
                    sublist.drop(1).all { it == DEAD_CELL }
            } == true) {

            // Удаляем последнюю LIFE после трех DEAD_CELL
            val lastIndex = items.value?.lastIndexOf(LIFE)
            if (lastIndex != null && lastIndex != -1) {
                items.value!![lastIndex] = DEAD_LIFE
                decreaseMapItem(LIFE)
                increaseMapItem(DEAD_LIFE)
            }
        }
    }

    private fun increaseMapItem(key: Int) {
        val map = itemTypesCounter.value ?: mutableMapOf()

        if (map.containsKey(key)) {
            map[key] = map[key]!! + 1
        } else {
            map[key] = 1
        }
        itemTypesCounter.value = map
    }

    private fun decreaseMapItem(key: Int) {
        val map = itemTypesCounter.value ?: mutableMapOf()
        if (map.containsKey(key)) {
            val currentValue = map[key]!!
            map[key] = maxOf(currentValue - 1, 0)
        }
        itemTypesCounter.value = map
    }
}
