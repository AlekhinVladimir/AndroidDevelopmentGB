package com.example.m12_mvvm.ui.theme.ui.main

sealed class State{
    data object Loading : State()
    data object Success : State()
    data class Result(val value: String? = null) : State()
}