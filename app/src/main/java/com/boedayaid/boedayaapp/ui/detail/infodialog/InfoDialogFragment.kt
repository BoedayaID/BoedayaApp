package com.boedayaid.boedayaapp.ui.detail.infodialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.boedayaid.boedayaapp.databinding.FragmentInfoDialogBinding
import com.bumptech.glide.Glide

class InfoDialogFragment : DialogFragment() {

    companion object {
        private const val IMAGE = "image"
        private const val TITLE = "title"
        private const val DESC = "desc"

        fun newInstance(image: String, title: String, desc: String): InfoDialogFragment =
            InfoDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(IMAGE, image)
                    putString(TITLE, title)
                    putString(DESC, desc)
                }
            }
    }

    private var _binding: FragmentInfoDialogBinding? = null
    val binding get() = _binding!!

    private lateinit var image: String
    private lateinit var title: String
    private lateinit var desc: String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        image = arguments?.getString(IMAGE) as String
        title = arguments?.getString(TITLE) as String
        desc = arguments?.getString(DESC) as String

        binding.apply {
            Glide.with(requireContext())
                .load(image)
                .into(imgPoster)
            tvTitle.text = title
            tvDesc.text = desc
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}