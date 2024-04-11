package project.moms.recycler.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import project.moms.recycler.data.Repository
import project.moms.recycler.models.MarsPhotosResponse
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _listPhotoMars = MutableStateFlow(MarsPhotosResponse(listOf()))
    val listPhotoMars = _listPhotoMars.asStateFlow()

    private val _state = MutableStateFlow(State.SUCCESS)
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.value = State.LOADING
            _listPhotoMars.value = repository.loadPhoto("2024-01-01")
            _state.value = State.SUCCESS
        }
    }

    fun refresh() {
        loadData()
    }
}