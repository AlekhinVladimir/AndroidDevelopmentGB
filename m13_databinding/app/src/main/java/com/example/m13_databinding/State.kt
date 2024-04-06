package com.example.m13_databinding

sealed class State {
    object Loading: State()
    object Success: State()
    object NoTextEnough: State()
}


