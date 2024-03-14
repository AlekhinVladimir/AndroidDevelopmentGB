package com.example.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.navigation.fragment.findNavController
import com.example.quiz.databinding.FragmentQuizBinding
import java.util.Locale


class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var quizStorage = QuizStorage.getQuiz(QuizStorage.Locale.En)
        if (Locale.getDefault().displayLanguage == "русский") {
            quizStorage = QuizStorage.getQuiz(QuizStorage.Locale.Ru)
        }
        setLocalData(quizStorage)

        binding.title.alpha = 0f
        binding.title.animate().apply {
            duration = 2000
            alpha(1f)
            start()
        }

        binding.button.setOnClickListener {
            val answers = getAnswersByUser(quizStorage)
            val action = QuizFragmentDirections.actionQuizFragmentToResultFragment(answers)
            findNavController().navigate(action)

        }
    }

    private fun setLocalData(local: Quiz) {
        val questions = local.questions

        binding.questionText1.text = questions[0].question
        binding.answer11.text = questions[0].answers[0]
        binding.answer12.text = questions[0].answers[1]
        binding.answer13.text = questions[0].answers[2]
        binding.answer14.text = questions[0].answers[3]

        binding.questionText2.text = questions[1].question
        binding.answer21.text = questions[1].answers[0]
        binding.answer22.text = questions[1].answers[1]
        binding.answer23.text = questions[1].answers[2]
        binding.answer24.text = questions[1].answers[3]

        binding.questionText3.text = questions[2].question
        binding.answer31.text = questions[2].answers[0]
        binding.answer32.text = questions[2].answers[1]
        binding.answer33.text = questions[2].answers[2]
        binding.answer34.text = questions[2].answers[3]
    }


    private fun getAnswersByUser(quizStorage: Quiz): String {
        val answers = mutableListOf<Int>()

        for (i in 0 until quizStorage.questions.size) {
            for (j in 0 until  quizStorage.questions[i].answers.size) {
                if (
                    quizStorage.questions[i].answers[j] == binding.root.findViewById<RadioButton>(binding.question1.checkedRadioButtonId).text ||
                    quizStorage.questions[i].answers[j] == binding.root.findViewById<RadioButton>(binding.question2.checkedRadioButtonId).text ||
                    quizStorage.questions[i].answers[j] == binding.root.findViewById<RadioButton>(binding.question3.checkedRadioButtonId).text
                ) answers += i
            }
        }

        return QuizStorage.answer(quizStorage, answers.toList())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}