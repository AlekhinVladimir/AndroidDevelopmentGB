package project.gb.cleanarchitecture.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import project.gb.cleanarchitecture.data.UsefulActivityDto
import project.gb.cleanarchitecture.domain.GetUsefulActivityUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getUsefulActivityUseCase: GetUsefulActivityUseCase
) : ViewModel() {

    private val activityPreview = UsefulActivityDto(
        "", "", 0, 0.0, "", "", 0.0
    )

    private val _activityStateFlow = MutableStateFlow(activityPreview)
    val activityStateFlow: StateFlow<UsefulActivityDto> = _activityStateFlow.asStateFlow()

    private val _state = MutableStateFlow(State.SUCCESS)
    val state = _state.asStateFlow()

    suspend fun reloadUsefulActivity() {
        _state.value = State.LOADING
        delay(1_000)
        val activity = getUsefulActivityUseCase.execute()
        _activityStateFlow.value = activity
        _state.value = State.SUCCESS
    }
}