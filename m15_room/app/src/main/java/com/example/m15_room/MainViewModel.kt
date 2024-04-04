package com.example.m15_room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m15_room.db.*

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(private val wordDao: WordDao) : ViewModel() {

    val maxCountStringSet: Flow<List<Word>> = wordDao.getMaxCountStringSet(5)

    fun onAddWordButtonClicked(newWord: String) {
        val word = Word(newWord, 0)

        viewModelScope.launch {
            val count: Int? = wordDao.getCount(newWord)
            word.count = when (count) {
                null -> 1
                else -> count + 1
            }
            when (word.count) {
                1 -> wordDao.insert(word)
                else -> wordDao.update(word)
            }
        }
    }

    fun onClearDBButtonClicked() {
        viewModelScope.launch {
            wordDao.delete()
        }
    }
}
