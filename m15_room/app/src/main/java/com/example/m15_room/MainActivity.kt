package com.example.m15_room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.m15_room.databinding.ActivityMainBinding
import com.example.m15_room.db.*
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val myRegExp = Pattern.compile("^[A-Za-zа-яА-Я-]{1,}$")
    private val viewModel: MainViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val wordDao: WordDao = (application as App).db.wordDao()
                return MainViewModel(wordDao) as T
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.wordInputField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val matcher = myRegExp.matcher(binding.wordInputField.text.toString())
                val isMatched = matcher.find()
                binding.addWordButton.isEnabled = isMatched
                if (!isMatched) binding.wordInputLayout.error = resources.getString(R.string.illegal_word_symbol)
                else binding.wordInputLayout.error = null
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.addWordButton.setOnClickListener {
            val newWord: String = binding.wordInputField.text.toString()
            viewModel.onAddWordButtonClicked(newWord)
        }
        binding.clearDBButton.setOnClickListener { viewModel.onClearDBButtonClicked() }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.maxCountStringSet.collect { stringSet: List<Word> ->
                    binding.resultTextView.text = getResultString(stringSet)
                }
            }
        }
    }
    private fun getResultString(wordList: List<Word>): String {
        var resultString = ""
        wordList.forEach { word ->
            resultString += "${word.word} - ${word.count} \r\n"
        }
        return resultString.trimEnd()
    }
}
