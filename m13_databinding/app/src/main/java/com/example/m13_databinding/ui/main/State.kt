package com.example.m13_databinding.ui.main


sealed class State(
    open val requestError: String? = null
) {
    data object Loading : State()
    data object Success : State()
    data class Error (
        override val requestError: String?
    ) : State(requestError = requestError)
}