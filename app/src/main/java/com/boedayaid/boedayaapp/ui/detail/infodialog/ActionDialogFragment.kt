package com.boedayaid.boedayaapp.ui.detail.infodialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.boedayaid.boedayaapp.data.model.DetailSuku
import com.boedayaid.boedayaapp.databinding.FragmentActionDialogBinding
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ActionDialogFragment : DialogFragment() {
    private var _binding: FragmentActionDialogBinding? = null
    val binding get() = _binding!!
    private val viewModel: ActionDialogViewModel by viewModels()

    private lateinit var tempatWisata: DetailSuku.TempatWisata
    private var user: FirebaseUser? = null

    private var stateBucket = false
    private var stateHistory = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        user = Firebase.auth.currentUser
        _binding = FragmentActionDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tempatWisata = arguments?.getParcelable(DATA)!!

        viewModel.checkOnBucket(user?.uid!!, tempatWisata)
        viewModel.checkOnHistory(user?.uid!!, tempatWisata)

        viewModel.isOnBucket.observe(viewLifecycleOwner) {
            binding.btnBucket.visibility = View.VISIBLE
            binding.btnBucket.isChecked = it
            stateBucket = it
        }

        viewModel.isOnHistory.observe(viewLifecycleOwner) {
            binding.btnHistory.visibility = View.VISIBLE
            binding.btnHistory.isChecked = it
            stateHistory = it
        }

        binding.apply {
            Glide.with(requireContext())
                .load(tempatWisata.gambar)
                .into(imgPoster)
            tvTitle.text = tempatWisata.namaTempat
            tvDesc.text = tempatWisata.deskripsi
        }

        binding.btnBucket.setOnClickListener {
            if (user != null) {
                if (stateBucket) {
                    viewModel.deleteOnBucket(user?.uid!!, tempatWisata)
                } else {
                    viewModel.setOnBucket(user?.uid!!, tempatWisata)
                }
            }
        }
        binding.btnHistory.setOnClickListener {
            if (user != null) {
                if (stateHistory) {
                    viewModel.deleteOnHistory(user?.uid!!, tempatWisata)
                } else {
                    viewModel.setOnHistory(user?.uid!!, tempatWisata)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val DATA = "data"

        fun newInstance(data: DetailSuku.TempatWisata): ActionDialogFragment =
            ActionDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(DATA, data)
                }
            }
    }
}