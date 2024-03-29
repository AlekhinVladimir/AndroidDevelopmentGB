package com.example.m12_mvvm
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class SearchViewModel : ViewModel() {
    private val _state=MutableStateFlow<State>(State.Success("", listOf()))
    val state=_state.asStateFlow()

    fun performSearch(search: String) {
        viewModelScope.launch {
            if (search.length < 3) {
                _state.value = State.Error("Введите больше 3-х символов")
                return@launch
            }
            _state.value=State.Loading(search)
            delay(2000)
            val results = listOf(
                State.Result("Результат 1"),
                State.Result("Результат 2"),
                State.Result("Результат 3")
            )
            _state.value=State.Success(search, results)
        }
    }

}
