package com.example.quiz

import com.example.quiz.Quiz

object QuizStorage {
    fun getQuiz(locale: Locale): Quiz = when (locale) {
        Locale.Ru -> quizRu
        Locale.En -> quizEn
    }

    fun answer(quiz: Quiz, answers: List<Int>): String = quiz
        .questions
        .zip(answers) { question, index -> question.feedback[index] }
        .joinToString(separator = " ")

    enum class Locale {
        Ru, En
    }

    private val quizRu = object : Quiz {
        override val questions: List<Question> = listOf(
            Question(
                question = "Сколько участников в kpop группе BTS?",
                answers = listOf(
                    "0",
                    "7",
                ),
                feedback = listOf(
                    "Вам не нравится наш курс.",
                    "Вас удовлетворяет наш курс.",
                    "Вы оценили наш курс хорошо.",
                    "Вы оценили наш курс отлично.",
                ),
            ),
            Question(
                question = "Как называется лейбл звукозаписи chief keef-a?",
                answers = listOf(
                    "GLO GANG",
                    "DRAIN GANG",
                ),
                feedback = listOf(
                    "Вы готовы порекомендовать наш курс.",
                    "Вы уже порекомендовали курс своим друзьям.",
                    "Вам нужно больше времени, чтобы порекомендовать наш курс.",
                    "Вы не готовы рекомендовать наш курс.",
                ),
            ),
            Question(
                question = "Настоящее имя исполнителя Blood Orange?",
                answers = listOf(
                    "Devonté Hynes",
                    "Rakim Athelaston Mayers",
                ),
                feedback = listOf(
                    "Спасибо за обратную связь!",
                    "Спасибо за обратную связь!",
                    "Ждем вашу обратную связь!",
                    "Мы надеемся, что у вас пояится возможность дать нам обратную связь.",
                ),
            ),
        )
    }


    private val quizEn = object : Quiz {
        override val questions: List<Question> = listOf(
            Question(
                question = "How many members are in kpop group BTS?",
                answers = listOf(
                    "0",
                    "7",
                ),
                feedback = listOf(
                    "You have rated us F",
                    "You have rated us C",
                    "You have rated us B",
                    "You have rated us A++",
                ),
            ),
            Question(
                question = "What is the name of the record label of chief keef-a?",
                answers = listOf(
                    "GLO GANG",
                    "DRAIN GANG",
                ),
                feedback = listOf(
                    "You want to recommend our course",
                    "You already recommend our course",
                    "You need more time for make recommendations",
                    "You are not ready to recommendations",
                ),
            ),
            Question(
                question = "The real name of the artist Blood Orange?",
                answers = listOf(
                    "Devonté Hynes",
                    "Rakim Athelaston Mayers",
                ),
                feedback = listOf(
                    "Thanks for feedback!",
                    "Thanks for feedback!",
                    "Waiting for your feedback!",
                    "We hope you will have the opportunity to give us feedback",
                ),
            ),
        )
    }
}