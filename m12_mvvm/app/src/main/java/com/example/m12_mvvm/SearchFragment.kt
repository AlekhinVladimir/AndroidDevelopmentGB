package com.example.m12_mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.m12_mvvm.databinding.FragmentSearchBinding
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            val search = binding.search.text.toString()
            viewModel.performSearch(search)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is State.Loading -> {
                            binding.progress.isVisible = true
                            binding.button.isEnabled =
                                false // Заблокировать кнопку во время загрузки
                        }

                        is State.Success -> {
                            binding.progress.isVisible = false
                            binding.button.isEnabled =
                                true // Разблокировать кнопку после успешного выполнения поиска

                            val searchText = state.search
                            val results = state.results

                            // Обновите UI с результатами поиска
                            binding.tvResult.text =
                                "По запросу $searchText найдено ${results.size} результатов"
                        }

                        is State.Error -> {
                            binding.progress.isVisible = false
                            binding.button.isEnabled = true

                            // Вывести сообщение об ошибке
                            Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }
}





