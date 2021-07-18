package com.boedayaid.boedayaapp.ui.profile.tab_layout.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.boedayaid.boedayaapp.databinding.FragmentListBinding
import com.boedayaid.boedayaapp.ui.detail_wisata.DetailWisataActivity
import com.boedayaid.boedayaapp.ui.profile.tab_layout.WisataGridAdapter
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ListFragment : Fragment() {

    private var user: FirebaseUser? = null

    private lateinit var type: String
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListViewModel by viewModels()
    private lateinit var wisataGridAdapter: WisataGridAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        user = Firebase.auth.currentUser
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        type = arguments?.getString(ARG_TYPE).toString()

        wisataGridAdapter = WisataGridAdapter()
        wisataGridAdapter.setOnClick { wisata ->
            Intent(requireContext(), DetailWisataActivity::class.java).also {
                it.putExtra("extra_data", wisata)
                startActivity(it)
            }
        }
        binding.list.apply {
            adapter = wisataGridAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
            setHasFixedSize(true)
        }


        viewModel.resultList.observe(viewLifecycleOwner) { result ->
            if (result.isEmpty()) {
                binding.status.visibility = View.VISIBLE
                binding.loading.visibility = View.GONE
                binding.status.text = "Kosong"
            } else {
                binding.status.visibility = View.GONE
                binding.loading.visibility = View.GONE
                wisataGridAdapter.setList(result)
            }
        }

        if (user != null) {
            if (type == "bucket") {
                viewModel.getBucketList(user?.uid!!)
            } else {
                viewModel.getHistoryList(user?.uid!!)
            }
        }
    }

    companion object {
        const val ARG_TYPE = "arg_type";

        @JvmStatic
        fun newInstance(type: String) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TYPE, type)
                }
            }
    }
}