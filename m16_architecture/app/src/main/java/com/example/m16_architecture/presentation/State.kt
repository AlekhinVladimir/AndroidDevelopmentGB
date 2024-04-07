package com.example.m16_architecture.presentation

import com.example.m16_architecture.entity.UsefulActivity


sealed class State {
    object ReadyForSearch : State()
    object LoadingActivity : State()
    object ErrorNoActivity : State()
    data class ActivityLoaded(val activity: UsefulActivity) : State()
    data class MainError(val message: String) : State()
}

