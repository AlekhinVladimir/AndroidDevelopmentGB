package project.moms.recycler.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import project.moms.recycler.R
import project.moms.recycler.databinding.FragmentPhotoFulScreenBinding
import project.moms.recycler.models.MarsPhoto


class FragmentPhotoFullScreen : Fragment() {
    private var _binding : FragmentPhotoFulScreenBinding? = null
    private val binding : FragmentPhotoFulScreenBinding
        get() {
            return _binding!!
        }

    private var param: MarsPhoto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param = it.getParcelable(KEY_PHOTO) as? MarsPhoto
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        printFullScreenPhoto()
    }

    private fun printFullScreenPhoto() {
        if (param != null) {
            Glide
                .with(requireContext())
                .load(param?.imgSrc)
                .into(binding.imageViewFullScreen)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPhotoFulScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val KEY_PHOTO = "KEY_PHOTO"
    }
}