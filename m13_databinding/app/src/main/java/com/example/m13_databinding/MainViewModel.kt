@file:OptIn(FlowPreview::class)

package com.example.m13_databinding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class MainViewModel : ViewModel() {
    private val _state = MutableStateFlow<State>(State.NoTextEnough)
    val state = _state.asStateFlow()
    var searchMessage: MutableLiveData<String> = MutableLiveData("Here will be results of searching...")
    val searchString = MutableStateFlow("")

    init {
        searchString.debounce(300).onEach {
            println(it)
            if(it.length >= 3){
                viewModelScope.launch {
                    _state.value = State.Loading
                    delay(5000)
                    _state.value = State.Success
                    searchMessage.value = "По запросу (${searchString.value}) ничего не найдено"
                }
            }
        }.launchIn(viewModelScope+Dispatchers.Default)
    }

}
