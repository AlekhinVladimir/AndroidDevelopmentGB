package com.example.m16_architecture.presentation

import android.util.Log
import androidx.lifecycle.*
import com.example.m16_architecture.domain.GetUsefulActivityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val VIEW_MODEL_LOG_TAG = "VIEW_MODEL_LOG"

@HiltViewModel
class MainViewModel @Inject constructor(private val activityUseCase: GetUsefulActivityUseCase) :
    ViewModel() {
    private val _state = MutableStateFlow<State>(State.ReadyForSearch)
    val state = _state.asStateFlow()

    fun reloadUsefulActivity() {
        _state.value = State.LoadingActivity
        viewModelScope.launch {
            try {
                val usefulActivity = activityUseCase.execute()
                _state.value = if (usefulActivity == null) {
                    State.ErrorNoActivity
                } else {
                    State.ActivityLoaded(usefulActivity)
                }
            } catch (e: Throwable) {
                Log.e(
                    VIEW_MODEL_LOG_TAG,
                    "$e \r\n cause: ${e.cause} \r\n suppressed by: ${e.suppressed.contentToString()}"
                )
                _state.value = State.MainError("$e")
            }
        }
    }
}
