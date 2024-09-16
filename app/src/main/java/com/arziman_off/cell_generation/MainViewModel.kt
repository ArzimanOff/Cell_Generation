package com.arziman_off.cell_generation

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Random

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val LOG_TAG = "NeedLogs"

    companion object {
        const val SEQUENCE_FOR_LIFE = 3
        const val SEQUENCE_FOR_LIFE_DELETE = 3

        const val DEAD_CELL = 0
        const val LIVING_CELL = 1

        const val SPEC_DEAD_CELL = 10
        const val SPEC_LIVING_CELL = 11

        const val LIFE = 2
        const val DEAD_LIFE = 3

        const val PREFS_NAME = "app_prefs"
        const val KEY_ITEMS = "key_items"
        const val KEY_ITEM_TYPES_COUNTER = "key_item_types_counter"
    }

    private val sharedPreferences: SharedPreferences =
        getApplication<Application>()
            .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)


    private val random = Random()
    val items: MutableLiveData<MutableList<Cell>> = MutableLiveData(mutableListOf())
    val itemTypesCounter: MutableLiveData<MutableMap<Int, Int>> = MutableLiveData(mutableMapOf())

    fun saveData() {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_ITEMS, Gson().toJson(items.value))
        editor.putString(KEY_ITEM_TYPES_COUNTER, Gson().toJson(itemTypesCounter.value))
        editor.apply()
    }

    fun loadData() {
        val itemsJson = sharedPreferences.getString(KEY_ITEMS, null)
        val counterJson = sharedPreferences.getString(KEY_ITEM_TYPES_COUNTER, null)

        items.value =
            Gson().fromJson(itemsJson, Array<Cell>::class.java)?.toMutableList() ?: mutableListOf()
        itemTypesCounter.value =
            Gson().fromJson(counterJson, object : TypeToken<MutableMap<Int, Int>>() {}.type)
                ?: mutableMapOf()
    }

    fun deleteAll() {
        items.postValue(mutableListOf())
        itemTypesCounter.postValue(mutableMapOf())
    }

    fun generateAndAddItem() {
        val newType = if (random.nextBoolean()) DEAD_CELL else LIVING_CELL
        addCell(newType)
        increaseMapItem(newType)

        checkNewLife()
        checkLifeDying()

        items.postValue(items.value)
    }

    fun addCell(type: Int) {
        val newId = items.value?.size ?: 0
        val newCell = Cell(id = newId, type = type, deadLifePosition = -1)
        val updatedList = items.value ?: mutableListOf()
        Log.d(LOG_TAG, type.toString())
        updatedList.add(newCell)
        items.value = updatedList
    }


    private fun checkNewLife() {
        if (items.value?.size!! >= SEQUENCE_FOR_LIFE &&
            items.value?.takeLast(SEQUENCE_FOR_LIFE)?.all
            { it.type == LIVING_CELL } == true
        ) {

            items.value?.let { currentList ->
                val updatedList = currentList.toMutableList().apply {
                    val lastThreeIndices = size - SEQUENCE_FOR_LIFE until size
                    lastThreeIndices.forEach { index ->
                        val cell = this[index]
                        this[index] = cell.copy(type = SPEC_LIVING_CELL)
                    }
                }
                items.value = updatedList
            }

            // Добавляем LIFE после трех LIVING_CELL
            addCell(LIFE)
            increaseMapItem(LIFE)
        }
    }


    private fun checkLifeDying() {
        if (items.value?.takeLast(SEQUENCE_FOR_LIFE_DELETE + 1)?.let { sublist ->
                sublist.first().type != DEAD_CELL &&
                        sublist.drop(1).all { it.type == DEAD_CELL }
            } == true) {

            val lastIndex = items.value?.indexOfLast { it.type == LIFE }
            if (lastIndex != null && lastIndex != -1) {

                items.value?.let { currentList ->
                    val updatedList = currentList.toMutableList().apply {
                        val lastThreeIndices = size - SEQUENCE_FOR_LIFE until size
                        lastThreeIndices.forEach { index ->
                            val cell = this[index]
                            this[index] =
                                cell.copy(type = SPEC_DEAD_CELL, deadLifePosition = lastIndex)
                        }
                    }
                    items.value = updatedList
                }

                val updatedList = items.value!!.toMutableList()
                val updatedCell = updatedList[lastIndex].copy(type = DEAD_LIFE)
                updatedList[lastIndex] = updatedCell
                items.value = updatedList

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
