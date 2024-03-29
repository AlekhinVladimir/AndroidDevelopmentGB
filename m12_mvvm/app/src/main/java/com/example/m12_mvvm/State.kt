package com.example.m12_mvvm

sealed class State {
    data class Loading(val search: String): State()
    data class Success(val search: String, val results: List<Result>): State()
    data class Result(val name: String)
    data class Error(val message: String) : State()
}
