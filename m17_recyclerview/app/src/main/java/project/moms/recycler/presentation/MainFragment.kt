package project.moms.recycler.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import project.moms.recycler.R
import project.moms.recycler.databinding.FragmentMainBinding
import project.moms.recycler.models.MarsPhoto
import project.moms.recycler.presentation.adapter.PhotoAdapter
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
    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    private lateinit var photoAdapter: PhotoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        photoAdapter = PhotoAdapter {marsPhoto ->  sendPhoto(marsPhoto)}

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }

        setupRecyclerView()
        observeViewModel()
        checkState()
    }

    private fun sendPhoto(item: MarsPhoto) {
        val bundle = Bundle().apply {
            putParcelable(FragmentPhotoFullScreen.KEY_PHOTO, item)
        }
        findNavController().navigate(R.id.action_mainFragment_to_fragmentPhotoFullScreen, bundle)
    }

    private fun setupRecyclerView() {
        binding.recycler.apply {
            adapter = photoAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.listPhotoMars.collect { listPhotoMars ->
                photoAdapter.setList(listPhotoMars.photos)
            }
        }
    }

    private fun checkState() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when(it) {
                    State.LOADING -> {
                        binding.swipeRefresh.isRefreshing = true
                    }
                    State.SUCCESS -> {
                        binding.swipeRefresh.isRefreshing = false
                    }
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}