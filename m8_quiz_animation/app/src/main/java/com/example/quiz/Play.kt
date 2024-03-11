package com.example.quiz

import java.util.Locale

fun main() {
    val questions = QuizStorage.getQuiz(QuizStorage.Locale.Ru).questions

    val local: String = Locale.getDefault().displayLanguage
    println(local)
}