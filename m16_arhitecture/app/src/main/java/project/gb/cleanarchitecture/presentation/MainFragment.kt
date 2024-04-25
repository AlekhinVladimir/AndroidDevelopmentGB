package project.gb.cleanarchitecture.presentation

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

import project.gb.cleanarchitecture.R
import project.gb.cleanarchitecture.adapter.UsefulActivityAdapter
import project.gb.cleanarchitecture.databinding.FragmentMainBinding
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding : FragmentMainBinding? = null
    private val binding : FragmentMainBinding
        get() {
            return _binding!!
        }

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory

    private val adapter: UsefulActivityAdapter by lazy { UsefulActivityAdapter() }

    private val viewModel: MainViewModel by viewModels {viewModelFactory}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.activityStateFlow.collect {activity ->
                adapter.updateData(listOf(activity))
            }
        }

        binding.refreshButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.reloadUsefulActivity()
            }
        }

        checkState()
    }

    private fun checkState() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when(it) {
                    State.SUCCESS -> {
                        binding.refreshButton.isEnabled = true
                        binding.progressBar.isVisible = false
                    }

                    State.LOADING -> {
                        binding.refreshButton.isEnabled = false
                        binding.progressBar.isVisible = true
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}