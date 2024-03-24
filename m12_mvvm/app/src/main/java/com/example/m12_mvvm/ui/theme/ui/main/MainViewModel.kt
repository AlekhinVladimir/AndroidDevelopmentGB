package com.example.m12_mvvm.ui.theme.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG="MainViewNodel"

class MainViewModel : ViewModel() {


    private val _state = MutableStateFlow<State>(State.Success)
    val state = _state.asStateFlow()


    fun onButtonSearchClick(query: String) {
        viewModelScope.launch {
            _state.value = State.Loading
            delay(5000)
            _state.value = State.Success
            _state.value = State.Result("По запросу $query ничего не найдено")
        }
    }
        override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared: ")
    }


}