package com.example.m13_databinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _state = MutableStateFlow<State>(State.Success("", listOf()))
    val state: StateFlow<State> = _state.asStateFlow()

    private var searchJob: Job? = null

    init {
            searchQuery
                .debounce(300)
                .filter { it.length >= 3 }
                .onEach { performSearch(it) }
                .launchIn(viewModelScope)
        }
    }
    fun performSearch(search: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _state.value = State.Loading(search)
            delay(2000) // Simulate network delay
            // Simulate search results
            val results = listOf(
                State.Result("Результат 1"),
                State.Result("Результат 2"),
                State.Result("Результат 3")
            )
            _state.value = State.Success(search, results)
        }
    }
}
