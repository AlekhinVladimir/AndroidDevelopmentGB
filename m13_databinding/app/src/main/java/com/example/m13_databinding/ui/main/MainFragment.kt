package com.example.m13_databinding.ui.main


import androidx.fragment.app.viewModels
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import com.example.m13_databinding.R
import com.example.m13_databinding.databinding.FragmentMainBinding



class MainFragment : Fragment() {

    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: M by ()
    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var constraintSetStart: ConstraintSet
    private lateinit var constraintSetEnd: ConstraintSet

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        constraintLayout = binding.mainConstraintLayout
        constraintSetStart = ConstraintSet()
        constraintSetStart.clone(constraintLayout)
        constraintSetEnd = ConstraintSet()
        constraintSetEnd.clone(requireContext(), R.layout.fragment_main_expanded)


        binding.textInputEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                expandEditText()
            }
        }

        binding.mainConstraintLayout.setOnClickListener {
            onEmptyAreaClicked()
            emptyAreaClickedForKeyboard()
        }

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                if (it is State.Loading) {
                    emptyAreaClickedForKeyboard()
                }
            }
        }
    }


    private fun expandEditText() {
        TransitionManager.beginDelayedTransition(constraintLayout, ChangeBounds().apply {
            duration = 300
            interpolator = AccelerateDecelerateInterpolator()
        })
        constraintSetEnd.applyTo(constraintLayout)
    }


    private fun collapseEditText() {
        TransitionManager.beginDelayedTransition(constraintLayout, ChangeBounds().apply {
            duration = 300
            interpolator = AccelerateDecelerateInterpolator()
        })
        constraintSetStart.applyTo(constraintLayout)
        binding.textInputEditText.clearFocus()
    }


    private fun onEmptyAreaClicked() {
        collapseEditText()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                if (it is State.Success) {
                    binding.progressBar.isVisible = false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


fun Fragment.emptyAreaClickedForKeyboard() = WindowCompat
    .getInsetsController(requireActivity().window, requireView())
    .hide(WindowInsetsCompat.Type.ime())

