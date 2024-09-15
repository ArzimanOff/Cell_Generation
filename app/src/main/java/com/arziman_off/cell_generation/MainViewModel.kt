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
    val items: MutableLiveData<MutableList<Cell>> = MutableLiveData(mutableListOf())
    val itemTypesCounter: MutableLiveData<MutableMap<Int, Int>> = MutableLiveData(mutableMapOf())


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
        val newCell = Cell(id = newId, type = type)
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

            // Добавляем LIFE после трех LIVING_CELL
            addCell(LIFE)
            increaseMapItem(LIFE)
        }
    }



private fun checkLifeDying() {
    // Проверяем, есть ли в конце нужная последовательность
    if (items.value?.takeLast(SEQUENCE_FOR_LIFE_DELETE + 1)?.let { sublist ->
            sublist.first().type != DEAD_CELL &&
                    sublist.drop(1).all { it.type == DEAD_CELL }
        } == true) {

        // Находим индекс последнего элемента с type == LIFE
        val lastIndex = items.value?.indexOfLast { it.type == LIFE }
        if (lastIndex != null && lastIndex != -1) {
            // Создаем новый список, чтобы вызвать обновление LiveData
            val updatedList = items.value!!.toMutableList()
            // Заменяем элемент на новый с type = DEAD_LIFE, сохраняя уникальный id
            val updatedCell = updatedList[lastIndex].copy(type = DEAD_LIFE)
            updatedList[lastIndex] = updatedCell
            // Обновляем LiveData новым списком
            items.value = updatedList

            // Обновляем счетчики
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
