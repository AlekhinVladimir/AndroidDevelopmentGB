package com.example.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        when (Locale.getDefault().displayLanguage) {
            "русский" -> setLocalData(QuizStorage.getQuiz(QuizStorage.Locale.Ru))
            "English" -> setLocalData(QuizStorage.getQuiz(QuizStorage.Locale.En))
        }

        binding.title.alpha = 0f
        binding.title.animate().apply {
            duration = 2000
            alpha(1f)
            start()
        }

        binding.button.setOnClickListener {
            val number = getAnswersByUser()
            val action = QuizFragmentDirections.actionQuizFragmentToResultFragment(number)
            findNavController().navigate(action)

        }
    }

    private fun setLocalData(local: Quiz) {
        val questions = local.questions

        binding.questionText1.text = questions[0].question
        binding.answer11.text = questions[0].answers[0]
        binding.answer12.text = questions[0].answers[1]

        binding.questionText2.text = questions[1].question
        binding.answer21.text = questions[1].answers[0]
        binding.answer22.text = questions[1].answers[1]

        binding.questionText3.text = questions[2].question
        binding.answer31.text = questions[2].answers[0]
        binding.answer32.text = questions[2].answers[1]

    }


    private fun getAnswersByUser(): Int {
        var correctAnswersCount = 0

        if (binding.question1.checkedRadioButtonId == binding.answer12.id) correctAnswersCount++
        if (binding.question2.checkedRadioButtonId == binding.answer21.id) correctAnswersCount++
        if (binding.question3.checkedRadioButtonId == binding.answer31.id) correctAnswersCount++

        return correctAnswersCount
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}