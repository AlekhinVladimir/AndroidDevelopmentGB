package com.example.m16_architecture.presentation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.m16_architecture.R
import com.example.m16_architecture.databinding.FragmentMainBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {
    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.refreshButton.setOnClickListener {
            viewModel.reloadUsefulActivity()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        State.LoadingActivity -> {
                            binding.refreshButton.isEnabled = false
                            binding.usefulActivityTextView.text = ""
                        }

                        State.ReadyForSearch -> {
                            binding.refreshButton.isEnabled = true
                        }

                        State.ErrorNoActivity -> {
                            binding.usefulActivityTextView.text =
                                resources.getString(R.string.useful_activity_default_text)
                        }

                        is State.ActivityLoaded -> {
                            binding.usefulActivityTextView.text = state.activity.activity
                        }

                        is State.MainError -> {
                            Snackbar.make(view, state.message, Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}

